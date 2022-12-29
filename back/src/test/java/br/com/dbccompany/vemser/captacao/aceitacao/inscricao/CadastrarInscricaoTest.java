package br.com.dbccompany.vemser.captacao.aceitacao.inscricao;


import br.com.dbccompany.vemser.captacao.builder.CandidatoBuilder;
import br.com.dbccompany.vemser.captacao.builder.FormularioBuilder;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoDTO;
import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioDTO;
import br.com.dbccompany.vemser.captacao.dto.inscricao.InscricaoDTO;
import br.com.dbccompany.vemser.captacao.service.CandidatoService;
import br.com.dbccompany.vemser.captacao.service.FormularioService;
import br.com.dbccompany.vemser.captacao.service.InscricaoService;
import br.com.dbccompany.vemser.captacao.utils.Utils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Inscrição")
@Epic("Cadastrar Inscrição")
public class CadastrarInscricaoTest {

    FormularioService formularioService = new FormularioService();
    FormularioBuilder formularioBuilder = new FormularioBuilder();
    CandidatoService candidatoService = new CandidatoService();
    CandidatoBuilder candidatoBuilder = new CandidatoBuilder();

    InscricaoService inscricaoService = new InscricaoService();

    @Test
    @Tag("all")
    @Description("Deve cadastrar inscricao candidato com sucesso")
    public void deveCadastrarInscricaoCandidatoComSucesso() {
        FormularioCreateDTO formularioCreate = formularioBuilder.criarFormulario();

        FormularioDTO formulario = formularioService.cadastrar(Utils.convertFormularioToJson(formularioCreate))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(FormularioDTO.class)
                ;

        CandidatoCreateDTO candidatoCreate = candidatoBuilder.criarCandidato();
        candidatoCreate.setFormulario(formulario.getIdFormulario());
        CandidatoDTO candidato = candidatoService.cadastroCandidato(Utils.convertCandidatoToJson(candidatoCreate))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().as(CandidatoDTO.class)
                ;
        InscricaoDTO inscricao = inscricaoService.cadastroInscricao(candidato.getIdCandidato())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(InscricaoDTO.class)
                ;
        assertEquals(candidato.getNome(), inscricao.getCandidato().getNome());
        assertEquals(candidato.getCidade(), inscricao.getCandidato().getCidade());
        assertEquals(candidato.getEmail(), inscricao.getCandidato().getEmail());
        assertEquals(candidato.getIdCandidato(), inscricao.getCandidato().getIdCandidato());

        inscricaoService.deletarInscricao(inscricao.getIdInscricao())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    @Tag("all")
    @Description("Deve tentar cadastrar inscricao candidato inexistente")
    public void deveCadastrarInscricaoCandidatoInexistente() {

        String message = inscricaoService.cadastroInscricao(0)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().path("message")
                ;
        assertEquals("Candidato não encontrado.", message);
    }
}
