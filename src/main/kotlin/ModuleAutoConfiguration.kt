import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.ComponentScan

@AutoConfiguration
@ComponentScan(value = ["exception"])
open class ModuleAutoConfiguration