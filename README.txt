# Individual do Boos

## Jar/Kotlin

O jar ao abrir, pega o id do processador da máquina que abriu automaticamente, para verificar se a máquina já foi cadastrada, e depois pergunta o login e a senha do usuário, que deve estar na base de dados da MedConnect para se autenticar. Após isso, inicia a captura de USB da máquina e com uma verificação única de desconexão do USB. Ou seja, sempre que um USB for desconectado ou conectado, é enviado ao banco de dados SQL Server.

## Python

O python do meu individual deve ser colocado dentro do diretório raiz do site do grupo (aonde fica a app.js), e essa aplicação deve estar dentro da máquina do windows server, que está sendo hosteada. No front-end ao usuário clicar para fazer a previsão dos componentes, ele faz uma requisição ao meu python pelo back-end, e o python pega do SQL Server os registros do componente e do robo mencionado, e retorna uma regressão linear desses dados ao back e front-end. Assim o usuário consegue ter uma dashboard de previsão futura dos dados e uma análise completa em cima dessa reta.
