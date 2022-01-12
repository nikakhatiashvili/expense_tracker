package com.example.finaleproject.util

import com.google.common.truth.Truth
import org.junit.Test

class StringExtensionsTest {
    @Test
    fun `empty email or empty password return false`(){
        val email = ""
        val pass = "sad"
        val name = ""
        val result = UIHelper.checkLogin(email,pass)
        val result1 = UIHelper.checkStrings(name,email,pass)
        Truth.assertThat(result).isFalse()
        Truth.assertThat(result1).isFalse()
    }

}