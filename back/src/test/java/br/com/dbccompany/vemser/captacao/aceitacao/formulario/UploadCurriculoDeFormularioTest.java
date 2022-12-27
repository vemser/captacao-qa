package br.com.dbccompany.vemser.captacao.aceitacao.formulario;

import br.com.dbccompany.vemser.captacao.builder.FormularioBuilder;
import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioDTO;
import br.com.dbccompany.vemser.captacao.service.FormularioService;
import br.com.dbccompany.vemser.captacao.utils.Utils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Formulário")
@Epic("Upload Currículo")
public class UploadCurriculoDeFormularioTest {

    FormularioService formularioService = new FormularioService();
    FormularioBuilder formularioBuilder = new FormularioBuilder();

    @Test
    @Tag("wip")
    @Description("Deve atualizar currículo de formulário com sucesso")
    public void deveAtualizarCurriculoDeFormularioComSucesso() {
        FormularioCreateDTO formularioCreate = formularioBuilder.criarFormulario();

        FormularioDTO formulario = formularioService.cadastrar(Utils.convertFormularioToJson(formularioCreate))
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(FormularioDTO.class)
                ;

        formularioService.atualizarCurriculo(formulario.getIdFormulario())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                ;

        assertNotNull(formulario.getCurriculo());

        formularioService.deletarTeste(formulario.getIdFormulario())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    @Tag("error")
    @Description("Deve não cadastrar formulário")
    public void deveNaoCadastrarFormularioSemPreencherCamposObrigatorios() {
        // DEVE RETORNAR MENSAGEM DE ERRO

        FormularioCreateDTO formularioCreate = formularioBuilder.criarFormularioSemPreencherCamposObrigatorios();

        formularioService.cadastrar(Utils.convertFormularioToJson(formularioCreate))
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    //.extract().path("message")
                ;

        //assertEquals("", message);
    }

    @Test
    @Tag("all")
    @Description("Deve não cadastrar formulário")
    public void deveNaoCadastrarFormularioSemMatricula() {
        FormularioCreateDTO formularioCreate = formularioBuilder.criarFormularioSemMatricula();

        String message = formularioService.cadastrar(Utils.convertFormularioToJson(formularioCreate))
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract().path("message")
                ;

        assertEquals("Precisa estar matriculado!", message);
    }

}