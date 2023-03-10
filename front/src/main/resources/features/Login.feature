#language:pt

@test
Funcionalidade: Login

  Contexto: O candidato deve logar no sistema

  @wip
  Cenario: Validar login com sucesso
    Dado que acesso a página de Login
    E preencho campo email de Login válido
    E preencho campo senha de Login válido
    Quando clico em 'LOGIN'
    Então devo ser redirecionado para a página de Candidatos

  @pendente
  Cenario: Validar tentativa de login sem preencher email e senha
    Dado que acesso a página de Login
    Quando clico em 'LOGIN'
    Então devo visualizar mensagens de erro para campos vazios na tela Login

  @pendente
  Cenario: Validar tentativa de login sem preencher email
    Dado que acesso a página de Login
    E não preencho campo email de Login
    E preencho campo senha de Login válido
    Quando clico em 'LOGIN'
    Então devo visualizar mensagen de erro para campo email vazio

  @pendente
  Cenario: Validar tentativa de login sem preencher senha
    Dado que acesso a página de Login
    E preencho campo email de Login válido
    E não preencho campo senha de Login
    Quando clico em 'LOGIN'
    Então devo visualizar mensagen de erro para campo senha vazio

  @pendente
  Cenario: Validar tentativa de login com email inválido
    Dado que acesso a página de Login
    E preencho campo email de Login inválido
    E preencho campo senha de Login válido
    Quando clico em 'LOGIN'
    Então devo visualizar mensagen de erro para campo email inválido

  @pendente
  Cenario: Validar tentativa de login com senha inválida
    Dado que acesso a página de Login
    E preencho campo email de Login válido
    E preencho campo senha de Login inválido
    Quando clico em 'LOGIN'
    Então devo visualizar mensagen de erro para campo senha inválido