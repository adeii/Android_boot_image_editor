package bootloader_message

import cfig.bootloader_message.BootloaderMsg
import org.junit.Test

import org.junit.Assert.*
import org.slf4j.LoggerFactory

@ExperimentalUnsignedTypes
class BootloaderMsgTest {
    private val log = LoggerFactory.getLogger(BootloaderMsgTest::class.java)

    @Test
    fun writeRebootBootloaderTest() {
        val msg = BootloaderMsg()
        msg.clearBootloaderMessage()
    }

    @Test
    fun readBootloaderMsgTest() {
        val msg = BootloaderMsg()
        msg.readBootloaderMsg()
        log.info(msg.toString())
    }

    @Test
    fun writeOptions() {
        val msg = BootloaderMsg()
        msg.updateBootloaderMessageInStruct(arrayOf(
                "--prompt_and_wipe_data",
                "--locale=zh_CN"))
        msg.writeBootloaderMessage()
    }


    @Test
    fun rebootRecovery() {
        val msg = BootloaderMsg()
        msg.updateBootloaderMessageInStruct(arrayOf())
        msg.writeBootloaderMessage()
    }

    @Test
    fun rebootCrash() {
        val msg = BootloaderMsg()
        msg.writeBootloaderMessage(arrayOf(
                "--prompt_and_wipe_data",
                "--reason=RescueParty",
                "--locale=en_US"))
    }

    @Test
    fun rebootOTA() {
        val msg = BootloaderMsg()
        msg.writeBootloaderMessage(arrayOf("--update_package=/cache/update.zip", "--security"))
    }
}
