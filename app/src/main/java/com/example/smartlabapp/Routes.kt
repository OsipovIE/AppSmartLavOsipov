package com.example.pr31

open class Routes(val route: String) {
    object WelcomeIn : Routes("WelcomeIn")
    object Autorization : Routes("Autorization")
    object AutorizationReg : Routes("AutorizationReg")
    object SavePwd : Routes("SavePwd")
    object Korz : Routes("Korz")
    object Oplata : Routes("Oplata")
    object OplataTrue : Routes("OplataTrue")
}