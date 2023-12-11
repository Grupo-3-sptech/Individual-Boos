package app
import LookaDados
import com.github.britooo.looca.api.core.Looca
import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.time.LocalDateTime
import javax.swing.JOptionPane
import java.io.File
import java.time.format.DateTimeFormatter

open class Main {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val looka1 = LookaDados()
            looka1.all()
        }
    }
}
