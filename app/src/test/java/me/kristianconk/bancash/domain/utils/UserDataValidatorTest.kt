package me.kristianconk.bancash.domain.utils

import org.junit.Test

class UserDataValidatorTest {

    private val systemUnderTest = UserDataValidator()

    @Test
    fun testValidPassword() {
        // invalid
        val emptyPass = ""
        val lowercasePass = "qwertyuiop"
        val uppercasePass = "QWERTYUIOP"
        val numberPass = "1234567890"
        val letterPass = "qwertyASDFGH"
        val alphanumberPass = "123456qwerty"
        val shortPass = "Abcd20"
        // valid
        val correctPass = "ABC123xyz"

        // assert
        assert(!systemUnderTest.isValidPassword(emptyPass)) { "Debería fallar con vacío" }
        assert(!systemUnderTest.isValidPassword(lowercasePass)) { "Debería fallar con solo minusculas" }
        assert(!systemUnderTest.isValidPassword(uppercasePass)) { "Debería fallar con vacío" }
        assert(!systemUnderTest.isValidPassword(numberPass)) { "Debería fallar con solo numeros" }
        assert(!systemUnderTest.isValidPassword(letterPass)) { "Debería fallar con solo letras" }
        assert(!systemUnderTest.isValidPassword(alphanumberPass)) { "Debería fallar con numeros y letras minuscula" }
        assert(!systemUnderTest.isValidPassword(shortPass)) { "Debería fallar con menos de 8 digitos" }
        assert(systemUnderTest.isValidPassword(correctPass)) { "Aqui no debe fallar" }
    }

    @Test
    fun testValidEmail() {
        // invalid
        val emptyEmail = ""
        val noDomainEmail = "qwertyuiop"
        val noAddressEmail = "@example.com"

        val dotWrong1Email = "username.@domain.com"
        val dotWrong2Email = ".user.name@domain.com"
        val dotWrong3Email = "user-name@domain.com."
        val wrongDomainEmail = "username@.com"

        // valid
        val correctEmail = "example@domain.com"

        // assert
        assert(!systemUnderTest.isEmailValid(emptyEmail)) { "Debe fallar con vacío" }
        assert(!systemUnderTest.isEmailValid(noDomainEmail)) { "Debe fallar con falta de dominio" }
        assert(!systemUnderTest.isEmailValid(noAddressEmail)) { "Debe fallar con falta de direccion" }
        assert(!systemUnderTest.isEmailValid(dotWrong1Email)) { "Debe fallar con punto antes del arroba" }
        assert(!systemUnderTest.isEmailValid(dotWrong2Email)) { "Debe fallar con punto al inicio" }
        assert(!systemUnderTest.isEmailValid(dotWrong3Email)) { "Debe fallar con punto al final" }
        assert(!systemUnderTest.isEmailValid(wrongDomainEmail)) { "Debe fallar con dominio mal formado" }
        assert(systemUnderTest.isEmailValid(correctEmail)) { "Aquí no debe fallar" }
    }

}