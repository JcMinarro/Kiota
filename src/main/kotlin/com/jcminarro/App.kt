package com.jcminarro

import com.google.gson.Gson
import com.xenomachina.argparser.ArgParser
import jota.IotaAPI

fun main(args: Array<String>) {
    println(KiotaCommander(KiotaArgs(ArgParser(args)).command).run())
}

class KiotaArgs(parse: ArgParser) {

    val command by parse.mapping(
            "--nodeInfo" to GetNodeInfoCommand,
            "-i" to GetNodeInfoCommand,
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
    }
}

sealed class KiotaCommand
object GetNodeInfoCommand : KiotaCommand()
