package com.dicoding.picodiploma.courseon

import com.google.firebase.auth.FirebaseAuth
import org.junit.Test
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
class GuruAdapterTest {
private lateinit var guruModel: GuruModel
    private val dummyResult = ""

    @Test
    fun showDialogOffline(){
        guruModel = GuruModel()
        val actual = guruModel.nama
        Assert.assertEquals(dummyResult, actual)
    }
}