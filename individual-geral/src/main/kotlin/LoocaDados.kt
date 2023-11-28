import com.github.britooo.looca.api.core.Looca
import com.github.britooo.looca.api.group.dispositivos.DispositivoUsb
import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.time.LocalDate
import java.time.LocalDateTime
import javax.swing.JOptionPane
import java.io.File
import java.time.format.DateTimeFormatter
import kotlin.concurrent.thread

fun main() {
    val looka1 = LookaDados()
    looka1.all()
}

class LookaDados {
    val looca = Looca()
    //    var bdInter: JdbcTemplate
    var bdInterServer: JdbcTemplate

    //id do processador de placeholder por enquanto.
    var id = Looca().processador.id

    init {
//        val dataSource = BasicDataSource()
//        dataSource.driverClassName = "com.mysql.cj.jdbc.Driver"
//        val serverName = "localhost"
//        val mydatabase = "medconnect"
//        dataSource.username = "root"
//        dataSource.password = "5505"
//        dataSource.url = "jdbc:mysql://$serverName/$mydatabase"
//        bdInter = JdbcTemplate(dataSource)

        //server

        val dataSoruceServer = BasicDataSource()
        dataSoruceServer.url = "jdbc:sqlserver://52.7.105.138:1433;databaseName=medconnect;encrypt=false";
        dataSoruceServer.driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
        dataSoruceServer.username = "sa"
        dataSoruceServer.password = "medconnect123"
        bdInterServer = JdbcTemplate(dataSoruceServer)
    }


    fun all() {

        println(id)
        var aPrimeiraVez: Boolean = ver()


        if (aPrimeiraVez == false) {
            while (true) {
                //   python()
//                sistema()
//                memoria()
//                processador()
//                grupoDeDiscos()
//                grupoDeServicos()
//                grupoDeProcessos()
                Dispositivo()
//                rede()
//                janelas()
                Thread.sleep(20 * 1000)
            }
        } else {
            cadastroUsu()
        }


    }

    //fun python(){
    //var arqPyConn = "SolucaoConn.py"
    //var pyExec: Process? = null
    //pyExec = Runtime.getRuntime().exec("python $arqPyConn")
    //}


    fun cadastroUsu() {
        var autorizacao = false

        var email: String = JOptionPane.showInputDialog("insira o seu email")
        var senha: String = JOptionPane.showInputDialog("insira sua senha")


        var fkHospital = bdInterServer.queryForObject(
            """
    SELECT fkHospital FROM Usuario WHERE email = '$email' AND senha = '$senha'
    """,
            Int::class.java
        )


        if (fkHospital != null) {
            autorizacao = true
        }


        if (autorizacao == true) {
            JOptionPane.showMessageDialog(
                null,
                "arraste o get-pip.py para a pasta public execute o arquivo InstalarPython.bat como adimistrador em seguida o InstalarPip.bat ambos como adimistrador, a instalao já está começando"
            )
//vamos ter que pensar regra de negocio ou script para o pythn ser instalado "aqui"
            cad(fkHospital)
        } else {
            println("problema na autenticação")
        }
    }

    fun cad(fkHospital: Int) {

        bdInterServer.execute(
            """
    INSERT INTO RoboCirurgiao (modelo, fabricacao, fkStatus, idProcess, fkHospital) 
    VALUES ('Modelo A', '${looca.processador.fabricante}', 1, '$id', $fkHospital)
""".trimIndent()
        )

        println("parabéns robo cadastrado baixando agora a solução MEDCONNECT")



        solucao()
        all()
    }


    fun downloadArq(url: URL, nomeArquivoDoPip: String) {
//funão de baixar arquivo da net
        url.openStream().use {
            Channels.newChannel(it).use { rbc ->
                FileOutputStream(nomeArquivoDoPip).use { fos -> //
                    fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE) //
                }
            }
        }
    }


    fun ver(): Boolean {
        //função que verifica se a maquina já foi usada antes


        val idRobo = bdInterServer.queryForObject(
            """
    SELECT COUNT(*) AS count FROM RoboCirurgiao WHERE idProcess = '$id'
    """,
            Int::class.java,
        )

        if (idRobo == 0) {
            return true
        } else {
            return false
        }


    }


    fun sistema() {
        val sistema = looca.sistema

        println(sistema)

        var fabricante = sistema.fabricante


        var incializando = sistema.inicializado

        var sistemaOperacional = sistema.sistemaOperacional

        println(looca.sistema.sistemaOperacional)

        var arquitetura = sistema.arquitetura

        var permissao = sistema.permissao

        var tempDeAtividade = sistema.tempoDeAtividade


    }

    fun memoria() {
        val memoria = looca.memoria

        var emUso = memoria.emUso

        var disponivel = memoria.disponivel

        var total = memoria.total
    }

    fun processador() {

        var processador = looca.processador

        var fabricante = processador.fabricante


        var frequencia = processador.frequencia

        var nome = processador.nome


        var identificador = processador.identificador


        var microarquitetura = processador.microarquitetura

        var numeroCpuFis = processador.numeroCpusFisicas

        var numCpuLogica = processador.numeroCpusLogicas

        var uso = processador.uso

        var numPacotFisico = processador.numeroPacotesFisicos


    }

    fun grupoDeDiscos() {
        val grupoDeDiscos = looca.grupoDeDiscos


        var qtdDeDisco = grupoDeDiscos.quantidadeDeDiscos
        //I live in the Rua hadock lobo Building on West 595 Street on the 2nd floor. My name is Enzo I’m 18 years old. There is an idea of a Enzo. Some kind of abstraction. But there is no real me.

        var discos = grupoDeDiscos.discos

        var volumes = grupoDeDiscos.volumes

        var tamanhoTotal = grupoDeDiscos.tamanhoTotal

        var qtdVolumes = grupoDeDiscos.quantidadeDeVolumes

        var nome = discos[0].nome

        var serial = discos[0].serial

    }

    fun grupoDeServicos() {
        val grupoDeServicos = looca.grupoDeServicos
        var servicos = grupoDeServicos.servicos
        var nome = servicos[0].nome
        var estado = servicos[0].estado
        var pid = servicos[0].pid
        var servicosAtivos = grupoDeServicos.servicosAtivos
        var sevicosInativos = grupoDeServicos.servicosInativos
        var totalDeServiços = grupoDeServicos.totalDeServicos
        var totalServicosAtivos = grupoDeServicos.totalServicosAtivos
        var totalServicosInativos = grupoDeServicos.totalServicosInativos
    }

    fun grupoDeProcessos() {
        val grupoDeProcessos = looca.grupoDeProcessos
        var processos = grupoDeProcessos.processos
        var totalProcessos = grupoDeProcessos.totalProcessos
        var totalThreads = grupoDeProcessos.totalThreads
    }


    fun Dispositivo() {
        val DispositivoUsbGp = looca.dispositivosUsbGrupo
        var totalConectados = DispositivoUsbGp.totalDispositvosUsbConectados
        var dispositivosUsb = DispositivoUsbGp.dispositivosUsb
        var dispositivosUsbConectados = DispositivoUsbGp.dispositivosUsbConectados
        var totalDispositvosUsb = DispositivoUsbGp.totalDispositvosUsb
        val dataHoraAtual = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val dataHoraFormatada = dataHoraAtual.format(formatter)

        val idRobo = bdInterServer.queryForObject(
            """
    select idRobo from RoboCirurgiao where idProcess = '$id'
    """,
            Int::class.java,
        )

        val nomesConectados = mutableListOf<String>()
        val nomesAtuais = mutableListOf<String>()
        val nomesContidos = mutableListOf<String>()
        val resultSet = bdInterServer.queryForRowSet("SELECT DISTINCT nome FROM dispositivos_usb")

        if (resultSet.next()) {
            do {
                val nome = resultSet.getString("nome")
                nomesConectados.add(nome)
            } while (resultSet.next())

            for (dispositivo in dispositivosUsb) {
                var nome = dispositivo.nome
                var idProduto = dispositivo.idProduto
                var fornecedor = dispositivo.forncecedor
                nomesAtuais.add(nome)

                if (nomesConectados.contains(nome)) {
                    nomesContidos.add(nome)
                    // Realize a inserção normal
                    bdInterServer.execute(
                        """
            INSERT INTO dispositivos_usb (nome, dataHora, id_produto, fornecedor, conectado, fkRoboUsb)
            VALUES ('$nome', '${dataHoraFormatada}', '$idProduto', '$fornecedor', 1, $idRobo);
            """
                    )
                }
            }
            println("Nomes Atuais" + nomesAtuais)
            println("Todos Nomes" + nomesConectados)
            val nomesNaoConectados = nomesConectados.subtract(nomesAtuais)

            for(disconectado in nomesNaoConectados){
                println("NOME NÃO CONECTADO! " + disconectado)
                bdInterServer.execute(
                    """
            INSERT INTO dispositivos_usb (nome, dataHora, id_produto, fornecedor, conectado, fkRoboUsb)
            VALUES ('$disconectado', '${dataHoraFormatada}', '$0x670b', '(Dispositivos padrão no sistema)', 0, $idRobo);
            """
                )
            }
            println("Nomes não conectados: " + nomesNaoConectados)
        } else {

            println("Nenhum dispositivo tinha sido inserido ainda!")
            for (dispositivo in dispositivosUsb) {
                var nome = dispositivo.nome
                var idProduto = dispositivo.idProduto
                var fornecedor = dispositivo.forncecedor

                bdInterServer.execute(
                    """
                    INSERT INTO dispositivos_usb (nome, dataHora, id_produto, fornecedor, conectado, fkRoboUsb)
                    VALUES ('$nome', '${dataHoraFormatada}', '$idProduto', '$fornecedor', 1, $idRobo);
                    """
                )
            }
        }


    }




    fun rede() {
        val rede = looca.rede
        println(rede)
        var parametros = rede.parametros
        var grupoDeInterfaces = rede.grupoDeInterfaces
    }

    fun janelas() {
        val janela = looca.grupoDeJanelas
        var janelas = janela.janelas
        var janelasVisiveis = janela.janelasVisiveis
        var totalJanelas = janela.totalJanelas
    }

    fun solucao() {
        val roboId = bdInterServer.queryForObject(
            """
    select idRobo from RoboCirurgiao where idProcess = '$id'
    """,
            Int::class.java,
        )


        var os: String = looca.sistema.sistemaOperacional

        if (os == "Windows") {
            val url = URL("https://bootstrap.pypa.io/get-pip.py")
            val nomeArquivoDoPip = "get-pip.py"
            downloadArq(url, nomeArquivoDoPip)
            println("Arquivo baixado com sucesso: $nomeArquivoDoPip")

            val nomeBash = "InstalarPip.bat"
            val arqBash = File(nomeBash)
            arqBash.writeText(
                "cd C:\\Users\\Public" +
                        "py get-pip.py"
            )
            val nomeBash2 = "InstalarDepPy.bat"
            val arqBash2 = File(nomeBash2)
            arqBash2.writeText(
                "cd C:\\Users\\Public" +
                        "pip install psutil" +
                        "pip install mysql-connector-python"+
                        "pip install ping3"
            )
            val nomeBash6 = "SolucaoMedConn.bat"
            val arqBash6 = File(nomeBash6)
            arqBash6.writeText(
                "py SolucaoConn.py"
            )


            val nomePy = "SolucaoConn.py"
            val arqPy = File(nomePy)
            arqPy.writeText("\n" +
                    "from mysql.connector import connect\n" +
                    "import psutil\n" +
                    "import platform\n" +
                    "import time\n" +
                    "import mysql.connector\n" +
                    "from datetime import datetime\n" +
                    "import ping3\n" +
                    "import json\n" +
                    "import requests\n" +
                    "import pymssql\n" +
                    "\n" +
                    "\n" +
                    "#alerta = {\"text\": \"alerta\"}\n" +
                    "\n" +
                    "webhook = \"https://hooks.slack.com/services/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\"\n" +
                    "#requests.post(webhook, data=json.dumps(alerta))\n" +
                    "\n" +
                    "\n" +
                    "idRobo = 1\n" +
                    "#descomente abaixo quando for ora criar esse arquivo pelo kotlin\n" +
                    "idRobo = ${roboId}\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "def mysql_connection(host, user, passwd, database=None):\n" +
                    "    connection = connect(\n" +
                    "        host=host,\n" +
                    "        user=user,\n" +
                    "        passwd=passwd,\n" +
                    "        database=database\n" +
                    "    )\n" +
                    "    return connection\n" +
                    "\n" +
                    "\n" +
                    "def bytes_para_gb(bytes_value):\n" +
                    "    return bytes_value / (1024 ** 3)\n" +
                    "\n" +
                    "def milissegundos_para_segundos(ms_value):\n" +
                    "    return ms_value / 1000\n" +
                    "\n" +
                    "\n" +
                    "connection = mysql_connection('localhost', 'medconnect', 'medconnect123', 'medconnect')\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "sqlserver_connection = pymssql.connect(server='52.7.105.138', database='medconnect', user='sa', password='medconnect123');\n" +
                    "\n" +
                    "\n" +
                    "#Disco\n" +
                    "\n" +
                    "meu_so = platform.system()\n" +
                    "if(meu_so == \"Linux\"):\n" +
                    "    nome_disco = '/'\n" +
                    "    disco = psutil.disk_usage(nome_disco)\n" +
                    "elif(meu_so == \"Windows\"):\n" +
                    "    nome_disco = 'C:\\\\'\n" +
                    "disco = psutil.disk_usage(nome_disco)\n" +
                    "discoPorcentagem = disco.percent\n" +
                    "discoTotal = \"{:.2f}\".format(bytes_para_gb(disco.total))\n" +
                    "discoUsado = \"{:.2f}\".format(bytes_para_gb(disco.used)) \n" +
                    "discoTempoLeitura = milissegundos_para_segundos(psutil.disk_io_counters(perdisk=False, nowrap=True)[4])\n" +
                    "discoTempoEscrita = milissegundos_para_segundos(psutil.disk_io_counters(perdisk=False, nowrap=True)[5])\n" +
                    "\n" +
                    "ins = [discoPorcentagem, discoTotal, discoUsado, discoTempoLeitura, discoTempoEscrita]\n" +
                    "componentes = [10,11,12,13,14]\n" +
                    "\n" +
                    "horarioAtual = datetime.now()\n" +
                    "horarioFormatado = horarioAtual.strftime('%Y-%m-%d %H:%M:%S')\n" +
                    "\n" +
                    "#banco de contenção\n" +
                    "cursor = connection.cursor()\n" +
                    "server_cursor = sqlserver_connection.cursor()\n" +
                    "\n" +
                    "queryExis = \"SELECT COUNT(*) AS count FROM RoboCirurgiao WHERE idRobo = 22\"\n" +
                    "    \n" +
                    "cursor.execute(queryExis)\n" +
                    "\n" +
                    "result = cursor.fetchone()\n" +
                    "if result[0] == 0:\n" +
                    "    querys = \"INSERT INTO RoboCirurgiao (idRobo, modelo, fabricacao, fkStatus, idProcess, fkHospital) VALUES (22, 'Modelo A', 'contenção', 1, 'a', 1)\"\n" +
                    "    cursor.execute(querys)\n" +
                    "    connection.commit()\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "for i in range(len(ins)):\n" +
                    "        \n" +
                    "    dado = ins[i]\n" +
                    "        \n" +
                    "    componente = componentes[i]\n" +
                    "    \n" +
                    "    query = \"INSERT INTO Registros (dado, fkRoboRegistro, fkComponente, HorarioDado) VALUES (%s, %s, %s, %s)\"\n" +
                    "\n" +
                    "    \n" +
                    "    \n" +
                    "    #banco de contenção\n" +
                    "    cursor.execute(query, (dado, 22, componente, horarioFormatado))\n" +
                    "    server_cursor.execute(query, (dado, idRobo, componente, horarioFormatado))\n" +
                    "\n" +
                    "\n" +
                    "print(\"\\nDisco porcentagem:\", discoPorcentagem,\n" +
                    "          \"\\nDisco total:\", discoTotal,\n" +
                    "          '\\nTempo de leitura do disco em segundos:', discoTempoLeitura,\n" +
                    "          '\\nTempo de escrita do disco em segundos:', discoTempoEscrita)\n" +
                    "\n" +
                    "\n" +
                    "while True:\n" +
                    "\n" +
                    "    #CPU\n" +
                    "    cpuPorcentagem = psutil.cpu_percent(None)\n" +
                    "    frequenciaCpuMhz = psutil.cpu_freq(percpu=False)\n" +
                    "    cpuVelocidadeEmGhz = \"{:.2f}\".format(frequenciaCpuMhz.current / 1000)\n" +
                    "    tempoSistema = psutil.cpu_times()[1] \n" +
                    "    processos = len(psutil.pids())\n" +
                    "    if(cpuPorcentagem > 60 and cpuPorcentagem > 70):\n" +
                    "        alerta = {\"text\": f\"alerta na cpu da maquina: {idRobo} está em estado de alerta\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))\n" +
                    "    if(cpuPorcentagem > 70 and cpuPorcentagem > 80):\n" +
                    "        alerta = {\"text\": f\"alerta na cpu da maquina: {idRobo} está em estado critico\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))\n" +
                    "    if(cpuPorcentagem > 80):\n" +
                    "        alerta = {\"text\": f\"alerta na cpu da maquina: {idRobo} está em estado de urgencia\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))\n" +
                    "        \n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "    \n" +
                    "    #Memoria\n" +
                    "    memoriaPorcentagem = psutil.virtual_memory()[2]\n" +
                    "    memoriaTotal = \"{:.2f}\".format(bytes_para_gb(psutil.virtual_memory().total))\n" +
                    "    memoriaUsada = \"{:.2f}\".format(bytes_para_gb(psutil.virtual_memory().used))\n" +
                    "    memoriaSwapPorcentagem = psutil.swap_memory().percent\n" +
                    "    memoriaSwapUso = \"{:.2f}\".format(bytes_para_gb(psutil.swap_memory().used))\n" +
                    "    if(memoriaPorcentagem > 60 and memoriaPorcentagem > 70):\n" +
                    "        alerta = {\"text\": f\"⚠️  Alerta na ram da maquina: {idRobo} está em estado de alerta\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))\n" +
                    "    if(memoriaPorcentagem > 70 and memoriaPorcentagem > 80):\n" +
                    "        alerta = {\"text\": f\"⚠️  Alerta na ram da maquina: {idRobo} está em estado critico\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))  \n" +
                    "    if(memoriaPorcentagem > 80):\n" +
                    "        alerta = {\"text\": f\" ⚠️  Alerta na ram da maquina: {idRobo} está em estado de urgencia\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))\n" +
                    "    \n" +
                    "    \"\"\"\n" +
                    "    Por enquanto não será usado\n" +
                    "    for particao in particoes:\n" +
                    "        try:\n" +
                    "            info_dispositivo = psutil.disk_usage(particao.mountpoint)\n" +
                    "            print(\"Ponto de Montagem:\", particao.mountpoint)\n" +
                    "            print(\"Sistema de Arquivos:\", particao.fstype)\n" +
                    "            print(\"Dispositivo:\", particao.device)\n" +
                    "            print(\"Espaço Total: {0:.2f} GB\".format(info_dispositivo.total / (1024 ** 3)) )\n" +
                    "            print(\"Espaço Usado: {0:.2f} GB\".format(info_dispositivo.used / (1024 ** 3)) )\n" +
                    "            print(\"Espaço Livre: {0:.2f} GB\".format(info_dispositivo.free / (1024 ** 3)) )\n" +
                    "            print(\"Porcentagem de Uso: {0:.2f}%\".format(info_dispositivo.percent))\n" +
                    "            print()\n" +
                    "        except PermissionError as e:\n" +
                    "            print(f\"Erro de permissão ao acessar {particao.mountpoint}: {e}\")\n" +
                    "        except Exception as e:\n" +
                    "            print(f\"Erro ao acessar {particao.mountpoint}: {e}\")\n" +
                    "            \"\"\"\n" +
                    "    #Rede\n" +
                    "    interval = 1\n" +
                    "    statusRede = 0\n" +
                    "    network_connections = psutil.net_connections()\n" +
                    "    network_active = any(conn.status == psutil.CONN_ESTABLISHED for conn in network_connections)\n" +
                    "    bytes_enviados = psutil.net_io_counters()[0]\n" +
                    "    bytes_recebidos = psutil.net_io_counters()[1]\n" +
                    "    \n" +
                    "    destino = \"google.com\"  \n" +
                    "    latencia = ping3.ping(destino) * 1000\n" +
                    "    if(latencia > 40 and latencia > 60):\n" +
                    "        alerta = {\"text\": f\"⚠️Alerta no ping da maquina: {idRobo} está em estado de alerta\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))\n" +
                    "    if(latencia > 60 and latencia > 80):\n" +
                    "        alerta = {\"text\": f\"⚠️Alerta no ping da maquina: {idRobo} está em estado critico\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))\n" +
                    "    if(latencia > 80):\n" +
                    "        alerta = {\"text\": f\"⚠️Alerta no ping da maquina: {idRobo} está em estado de urgencia\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))\n" +
                    "    \n" +
                    "    if latencia is not None:\n" +
                    "        print(f\"Latência para {destino}: {latencia:.2f} ms\")\n" +
                    "    else:\n" +
                    "        print(f\"Não foi possível alcançar {destino}\")  \n" +
                    "\n" +
                    "    \n" +
                    "    if network_active:\n" +
                    "\n" +
                    "        print (\"A rede está ativa.\")\n" +
                    "        statusRede= 1\n" +
                    "    else:\n" +
                    "\n" +
                    "        print (\"A rede não está ativa.\")\n" +
                    "\n" +
                    "    #Outros\n" +
                    "    boot_time = datetime.fromtimestamp(psutil.boot_time()).strftime(\"%Y-%m-%d %H:%M:%S\")\n" +
                    "    print(\"A maquina está ligada desde: \",boot_time)\n" +
                    "\n" +
                    "    horarioAtual = datetime.now()\n" +
                    "    horarioFormatado = horarioAtual.strftime('%Y-%m-%d %H:%M:%S')\n" +
                    "    \n" +
                    "    ins = [cpuPorcentagem, cpuVelocidadeEmGhz, tempoSistema, processos, memoriaPorcentagem,\n" +
                    "           memoriaTotal, memoriaUsada, memoriaSwapPorcentagem, memoriaSwapUso, statusRede, latencia,\n" +
                    "           bytes_enviados, bytes_recebidos]\n" +
                    "    componentes = [1,2,3,4,5,6,7,8,9,15,16,17,18]\n" +
                    "    \n" +
                    "    cursor = connection.cursor()\n" +
                    "    server_cursor = sqlserver_connection.cursor()\n" +
                    "    \n" +
                    "    for i in range(len(ins)):\n" +
                    "        dado = ins[i]\n" +
                    "        componente = componentes[i]\n" +
                    "\n" +
                    "        query = \"INSERT INTO Registros (dado, fkRoboRegistro, fkComponente, HorarioDado) VALUES (%s, %s, %s, %s)\"\n" +
                    "\n" +
                    "\n" +
                    "        #banco de contenção abaixo\n" +
                    "        cursor.execute(query, (dado, 22, componente, horarioFormatado))\n" +
                    "        server_cursor.execute(query, (dado, idRobo, componente, horarioFormatado))\n" +
                    "\n" +
                    "\n" +
                    "        \n" +
                    "        connection.commit()\n" +
                    "        sqlserver_connection.commit()\n" +
                    "\n" +
                    "       \n" +
                    "    print(\"\\nINFORMAÇÕES SOBRE PROCESSAMENTO: \")\n" +
                    "    print('\\nPorcentagem utilizada da CPU: ',cpuPorcentagem,\n" +
                    "          '\\nVelocidade da CPU: ',cpuVelocidadeEmGhz,\n" +
                    "          '\\nTempo de atividade da CPU: ', tempoSistema,\n" +
                    "          '\\nNumero de processos: ', processos,\n" +
                    "          '\\nPorcentagem utilizada de memoria: ', memoriaPorcentagem,\n" +
                    "          '\\nQuantidade usada de memoria: ', memoriaTotal,\n" +
                    "          '\\nPorcentagem usada de memoria Swap: ', memoriaSwapPorcentagem,\n" +
                    "          '\\nQuantidade usada de memoria Swap: ', memoriaSwapUso,\n" +
                    "          '\\nBytes enviados', bytes_enviados,\n" +
                    "          '\\nBytes recebidos', bytes_recebidos)\n" +
                    "   \n" +
                    "    \n" +
                    "       \n" +
                    "\n" +
                    "\n" +
                    "    time.sleep(5)\n" +
                    "\n" +
                    "cursor.close()\n" +
                    "server_cursor.close()\n" +
                    "connection.close()\n" +
                    "sqlserver_connection.close()\n" +
                    "    \n"
            )

            val nomeBash1 = "InstalarPython.bat"
            val arqBash1 = File(nomeBash1)
            arqBash1.writeText(
                // escreve um comando de script para instalar o py usando chocolatey
                "@\"%SystemRoot%\\System32\\WindowsPowerShell\\v1.0\\powershell.exe\" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command \"iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))\" && SET \"PATH=%PATH%;%ALLUSERSPROFILE%\\chocolatey\\bin\"\n\n" +
                        "choco install python311 --params \"/C:\\Users\\Public\""
            )
        } else {
            //aqui é a instalção da solução se a maquina não for baseadinho windows
            val url = URL("https://bootstrap.pypa.io/get-pip.py")
            val nomeArquivoDoPip = "get-pip.py"
            downloadArq(url, nomeArquivoDoPip)
            println("Arquivo baixado com sucesso: $nomeArquivoDoPip")

            //boa sorte kayk fazendo esses .sh

            val nomeBashLinux = "InstalarPip.sh"
            val arqBashLinux = File(nomeBashLinux)
            arqBashLinux.writeText(
                ""
            )

            val nomeBash2Linux = "InstalarDepPy.sh"
            val arqBash2Linux = File(nomeBash2Linux)
            arqBash2Linux.writeText(
                ""
            )

            val nomeBash1Linux = "InstalarPython.sh"
            val arqBash1Linux = File(nomeBash1Linux)
            arqBash1Linux.writeText(
                // escreve um comando de script para instalar o py usando chocolatey
                ""
            )

            val nomePyLinux = "SolucaoConn.py"
            val arqPyLinux = File(nomePyLinux)
            arqPyLinux.writeText("\n" +
                    "from mysql.connector import connect\n" +
                    "import psutil\n" +
                    "import platform\n" +
                    "import time\n" +
                    "import mysql.connector\n" +
                    "from datetime import datetime\n" +
                    "import ping3\n" +
                    "import json\n" +
                    "import requests\n" +
                    "import pymssql\n" +
                    "\n" +
                    "\n" +
                    "#alerta = {\"text\": \"alerta\"}\n" +
                    "\n" +
                    "webhook = \"https://hooks.slack.com/services/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\"\n" +
                    "#requests.post(webhook, data=json.dumps(alerta))\n" +
                    "\n" +
                    "\n" +
                    "idRobo = 1\n" +
                    "#descomente abaixo quando for ora criar esse arquivo pelo kotlin\n" +
                    "#idRobo = ${roboId}\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "def mysql_connection(host, user, passwd, database=None):\n" +
                    "    connection = connect(\n" +
                    "        host=host,\n" +
                    "        user=user,\n" +
                    "        passwd=passwd,\n" +
                    "        database=database\n" +
                    "    )\n" +
                    "    return connection\n" +
                    "\n" +
                    "\n" +
                    "def bytes_para_gb(bytes_value):\n" +
                    "    return bytes_value / (1024 ** 3)\n" +
                    "\n" +
                    "def milissegundos_para_segundos(ms_value):\n" +
                    "    return ms_value / 1000\n" +
                    "\n" +
                    "\n" +
                    "connection = mysql_connection('localhost', 'root', '5505', 'medconnect')\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "sqlserver_connection = pymssql.connect(server='52.7.105.138', database='medconnect', user='sa', password='medconnect123');\n" +
                    "\n" +
                    "\n" +
                    "#Disco\n" +
                    "\n" +
                    "meu_so = platform.system()\n" +
                    "if(meu_so == \"Linux\"):\n" +
                    "    nome_disco = '/'\n" +
                    "    disco = psutil.disk_usage(nome_disco)\n" +
                    "elif(meu_so == \"Windows\"):\n" +
                    "    nome_disco = 'C:\\\\'\n" +
                    "disco = psutil.disk_usage(nome_disco)\n" +
                    "discoPorcentagem = disco.percent\n" +
                    "discoTotal = \"{:.2f}\".format(bytes_para_gb(disco.total))\n" +
                    "discoUsado = \"{:.2f}\".format(bytes_para_gb(disco.used)) \n" +
                    "discoTempoLeitura = milissegundos_para_segundos(psutil.disk_io_counters(perdisk=False, nowrap=True)[4])\n" +
                    "discoTempoEscrita = milissegundos_para_segundos(psutil.disk_io_counters(perdisk=False, nowrap=True)[5])\n" +
                    "\n" +
                    "ins = [discoPorcentagem, discoTotal, discoUsado, discoTempoLeitura, discoTempoEscrita]\n" +
                    "componentes = [10,11,12,13,14]\n" +
                    "\n" +
                    "horarioAtual = datetime.now()\n" +
                    "horarioFormatado = horarioAtual.strftime('%Y-%m-%d %H:%M:%S')\n" +
                    "\n" +
                    "#banco de contenção\n" +
                    "cursor = connection.cursor()\n" +
                    "server_cursor = sqlserver_connection.cursor()\n" +
                    "\n" +
                    "queryExis = \"SELECT COUNT(*) AS count FROM RoboCirurgiao WHERE idRobo = 22\"\n" +
                    "    \n" +
                    "cursor.execute(queryExis)\n" +
                    "\n" +
                    "result = cursor.fetchone()\n" +
                    "if result[0] == 0:\n" +
                    "    querys = \"INSERT INTO RoboCirurgiao (idRobo, modelo, fabricacao, fkStatus, idProcess, fkHospital) VALUES (22, 'Modelo A', 'contenção', 1, 'a', 1)\"\n" +
                    "    cursor.execute(querys)\n" +
                    "    connection.commit()\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "for i in range(len(ins)):\n" +
                    "        \n" +
                    "    dado = ins[i]\n" +
                    "        \n" +
                    "    componente = componentes[i]\n" +
                    "    \n" +
                    "    query = \"INSERT INTO Registros (dado, fkRoboRegistro, fkComponente, HorarioDado) VALUES (%s, %s, %s, %s)\"\n" +
                    "\n" +
                    "    \n" +
                    "    \n" +
                    "    #banco de contenção\n" +
                    "    cursor.execute(query, (dado, 22, componente, horarioFormatado))\n" +
                    "    server_cursor.execute(query, (dado, idRobo, componente, horarioFormatado))\n" +
                    "\n" +
                    "\n" +
                    "print(\"\\nDisco porcentagem:\", discoPorcentagem,\n" +
                    "          \"\\nDisco total:\", discoTotal,\n" +
                    "          '\\nTempo de leitura do disco em segundos:', discoTempoLeitura,\n" +
                    "          '\\nTempo de escrita do disco em segundos:', discoTempoEscrita)\n" +
                    "\n" +
                    "\n" +
                    "while True:\n" +
                    "\n" +
                    "    #CPU\n" +
                    "    cpuPorcentagem = psutil.cpu_percent(None)\n" +
                    "    frequenciaCpuMhz = psutil.cpu_freq(percpu=False)\n" +
                    "    cpuVelocidadeEmGhz = \"{:.2f}\".format(frequenciaCpuMhz.current / 1000)\n" +
                    "    tempoSistema = psutil.cpu_times()[1] \n" +
                    "    processos = len(psutil.pids())\n" +
                    "    if(cpuPorcentagem > 60 and cpuPorcentagem > 70):\n" +
                    "        alerta = {\"text\": f\"alerta na cpu da maquina: {idRobo} está em estado de alerta\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))\n" +
                    "    if(cpuPorcentagem > 70 and cpuPorcentagem > 80):\n" +
                    "        alerta = {\"text\": f\"alerta na cpu da maquina: {idRobo} está em estado critico\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))\n" +
                    "    if(cpuPorcentagem > 80):\n" +
                    "        alerta = {\"text\": f\"alerta na cpu da maquina: {idRobo} está em estado de urgencia\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))\n" +
                    "        \n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "    \n" +
                    "    #Memoria\n" +
                    "    memoriaPorcentagem = psutil.virtual_memory()[2]\n" +
                    "    memoriaTotal = \"{:.2f}\".format(bytes_para_gb(psutil.virtual_memory().total))\n" +
                    "    memoriaUsada = \"{:.2f}\".format(bytes_para_gb(psutil.virtual_memory().used))\n" +
                    "    memoriaSwapPorcentagem = psutil.swap_memory().percent\n" +
                    "    memoriaSwapUso = \"{:.2f}\".format(bytes_para_gb(psutil.swap_memory().used))\n" +
                    "    if(memoriaPorcentagem > 60 and memoriaPorcentagem > 70):\n" +
                    "        alerta = {\"text\": f\"⚠️  Alerta na ram da maquina: {idRobo} está em estado de alerta\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))\n" +
                    "    if(memoriaPorcentagem > 70 and memoriaPorcentagem > 80):\n" +
                    "        alerta = {\"text\": f\"⚠️  Alerta na ram da maquina: {idRobo} está em estado critico\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))  \n" +
                    "    if(memoriaPorcentagem > 80):\n" +
                    "        alerta = {\"text\": f\" ⚠️  Alerta na ram da maquina: {idRobo} está em estado de urgencia\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))\n" +
                    "    \n" +
                    "    \"\"\"\n" +
                    "    Por enquanto não será usado\n" +
                    "    for particao in particoes:\n" +
                    "        try:\n" +
                    "            info_dispositivo = psutil.disk_usage(particao.mountpoint)\n" +
                    "            print(\"Ponto de Montagem:\", particao.mountpoint)\n" +
                    "            print(\"Sistema de Arquivos:\", particao.fstype)\n" +
                    "            print(\"Dispositivo:\", particao.device)\n" +
                    "            print(\"Espaço Total: {0:.2f} GB\".format(info_dispositivo.total / (1024 ** 3)) )\n" +
                    "            print(\"Espaço Usado: {0:.2f} GB\".format(info_dispositivo.used / (1024 ** 3)) )\n" +
                    "            print(\"Espaço Livre: {0:.2f} GB\".format(info_dispositivo.free / (1024 ** 3)) )\n" +
                    "            print(\"Porcentagem de Uso: {0:.2f}%\".format(info_dispositivo.percent))\n" +
                    "            print()\n" +
                    "        except PermissionError as e:\n" +
                    "            print(f\"Erro de permissão ao acessar {particao.mountpoint}: {e}\")\n" +
                    "        except Exception as e:\n" +
                    "            print(f\"Erro ao acessar {particao.mountpoint}: {e}\")\n" +
                    "            \"\"\"\n" +
                    "    #Rede\n" +
                    "    interval = 1\n" +
                    "    statusRede = 0\n" +
                    "    network_connections = psutil.net_connections()\n" +
                    "    network_active = any(conn.status == psutil.CONN_ESTABLISHED for conn in network_connections)\n" +
                    "    bytes_enviados = psutil.net_io_counters()[0]\n" +
                    "    bytes_recebidos = psutil.net_io_counters()[1]\n" +
                    "    \n" +
                    "    destino = \"google.com\"  \n" +
                    "    latencia = ping3.ping(destino) * 1000\n" +
                    "    if(latencia > 40 and latencia > 60):\n" +
                    "        alerta = {\"text\": f\"⚠️Alerta no ping da maquina: {idRobo} está em estado de alerta\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))\n" +
                    "    if(latencia > 60 and latencia > 80):\n" +
                    "        alerta = {\"text\": f\"⚠️Alerta no ping da maquina: {idRobo} está em estado critico\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))\n" +
                    "    if(latencia > 80):\n" +
                    "        alerta = {\"text\": f\"⚠️Alerta no ping da maquina: {idRobo} está em estado de urgencia\"}\n" +
                    "        requests.post(webhook, data=json.dumps(alerta))\n" +
                    "    \n" +
                    "    if latencia is not None:\n" +
                    "        print(f\"Latência para {destino}: {latencia:.2f} ms\")\n" +
                    "    else:\n" +
                    "        print(f\"Não foi possível alcançar {destino}\")  \n" +
                    "\n" +
                    "    \n" +
                    "    if network_active:\n" +
                    "\n" +
                    "        print (\"A rede está ativa.\")\n" +
                    "        statusRede= 1\n" +
                    "    else:\n" +
                    "\n" +
                    "        print (\"A rede não está ativa.\")\n" +
                    "\n" +
                    "    #Outros\n" +
                    "    boot_time = datetime.fromtimestamp(psutil.boot_time()).strftime(\"%Y-%m-%d %H:%M:%S\")\n" +
                    "    print(\"A maquina está ligada desde: \",boot_time)\n" +
                    "\n" +
                    "    horarioAtual = datetime.now()\n" +
                    "    horarioFormatado = horarioAtual.strftime('%Y-%m-%d %H:%M:%S')\n" +
                    "    \n" +
                    "    ins = [cpuPorcentagem, cpuVelocidadeEmGhz, tempoSistema, processos, memoriaPorcentagem,\n" +
                    "           memoriaTotal, memoriaUsada, memoriaSwapPorcentagem, memoriaSwapUso, statusRede, latencia,\n" +
                    "           bytes_enviados, bytes_recebidos]\n" +
                    "    componentes = [1,2,3,4,5,6,7,8,9,15,16,17,18]\n" +
                    "    \n" +
                    "    cursor = connection.cursor()\n" +
                    "    server_cursor = sqlserver_connection.cursor()\n" +
                    "    \n" +
                    "    for i in range(len(ins)):\n" +
                    "        dado = ins[i]\n" +
                    "        componente = componentes[i]\n" +
                    "\n" +
                    "        query = \"INSERT INTO Registros (dado, fkRoboRegistro, fkComponente, HorarioDado) VALUES (%s, %s, %s, %s)\"\n" +
                    "\n" +
                    "\n" +
                    "        #banco de contenção abaixo\n" +
                    "        cursor.execute(query, (dado, 22, componente, horarioFormatado))\n" +
                    "        server_cursor.execute(query, (dado, idRobo, componente, horarioFormatado))\n" +
                    "\n" +
                    "\n" +
                    "        \n" +
                    "        connection.commit()\n" +
                    "        sqlserver_connection.commit()\n" +
                    "\n" +
                    "       \n" +
                    "    print(\"\\nINFORMAÇÕES SOBRE PROCESSAMENTO: \")\n" +
                    "    print('\\nPorcentagem utilizada da CPU: ',cpuPorcentagem,\n" +
                    "          '\\nVelocidade da CPU: ',cpuVelocidadeEmGhz,\n" +
                    "          '\\nTempo de atividade da CPU: ', tempoSistema,\n" +
                    "          '\\nNumero de processos: ', processos,\n" +
                    "          '\\nPorcentagem utilizada de memoria: ', memoriaPorcentagem,\n" +
                    "          '\\nQuantidade usada de memoria: ', memoriaTotal,\n" +
                    "          '\\nPorcentagem usada de memoria Swap: ', memoriaSwapPorcentagem,\n" +
                    "          '\\nQuantidade usada de memoria Swap: ', memoriaSwapUso,\n" +
                    "          '\\nBytes enviados', bytes_enviados,\n" +
                    "          '\\nBytes recebidos', bytes_recebidos)\n" +
                    "   \n" +
                    "    \n" +
                    "       \n" +
                    "\n" +
                    "\n" +
                    "    time.sleep(5)\n" +
                    "\n" +
                    "cursor.close()\n" +
                    "server_cursor.close()\n" +
                    "connection.close()\n" +
                    "sqlserver_connection.close()\n" +
                    "    \n")


        }
    }
}
