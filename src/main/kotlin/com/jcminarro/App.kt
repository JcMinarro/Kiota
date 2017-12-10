package com.jcminarro

import com.google.gson.Gson
import com.xenomachina.argparser.ArgParser
import jota.IotaAPI

fun main(args: Array<String>) {
    println(KiotaCommander(KiotaArgs(ArgParser(args)).command).run())
}

class KiotaArgs(parse: ArgParser) {

    private val addresses by parse.adding("-a", help = "Address Direcction")

    val command by parse.mapping(
            "--nodeInfo" to GetNodeInfoCommand,
            "-i" to GetNodeInfoCommand,
            "--findByAddress" to FindByAddressCommand({addresses.toTypedArray()}),
            help = "Command")
}

class KiotaCommander(private val command: KiotaCommand) {

    private val iotaApi: IotaAPI =
            IotaAPI.Builder()
                    .protocol("http")
                    .host("eugene.iotasupport.com")
                    .port("14999")
                    .build()


    fun run(): String = when (command) {
        GetNodeInfoCommand -> Gson().toJson(iotaApi.nodeInfo)
        is FindByAddressCommand -> Gson().toJson(iotaApi.findTransactionObjectsByAddresses(command.addresses))
    }
}

sealed class KiotaCommand
object GetNodeInfoCommand : KiotaCommand()
data class FindByAddressCommand(private val getAddressesFunction: () -> Array<String>) : KiotaCommand(){
    val addresses
        get() = getAddressesFunction()
}
