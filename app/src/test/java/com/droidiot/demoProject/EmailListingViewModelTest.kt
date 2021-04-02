package com.droidiot.demoProject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.droidiot.demoProject.data.DataManager
import com.droidiot.demoProject.data.model.EmailModel
import com.droidiot.demoProject.data.network.ApiHelper
import com.droidiot.demoProject.ui.emailListing.EmailListingViewModel
import com.droidiot.demoProject.util.MockScheduleProviderImpl
import com.droidiot.demoProject.util.RxSchedulersOverrideRule
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit

class EmailListingViewModelTest {


    @JvmField
    @Rule
    var schedulersOverrideRule = RxSchedulersOverrideRule()

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()


    @Mock
    lateinit var apiHelper: ApiHelper

    @Mock
    lateinit var dataManager: DataManager


    lateinit var model: EmailListingViewModel

    @Before
    fun setupDependency() {
        //here we will setup out viewModel
        MockitoAnnotations.initMocks(this)
        println("Dependency Setup")
        dataManager = DataManager(apiHelper)
    }


    @Test
    fun `Given DataManager returns Empty data, when getEmailList() called, then update live data`() {
        //Setting how up the mock behaves
        model =
            EmailListingViewModel(dataManager, CompositeDisposable(), MockScheduleProviderImpl())
        `when`(dataManager.getEmailList())
            .thenReturn(Single.just(arrayListOf()))
        //Fire the test method
        model.getEmailList()
        //Check that our live data is updated
        Assert.assertEquals(arrayListOf<EmailModel>(), model.responseListData.value!!.data)
    }

    @Test
    fun `Given DataManager returns Error, when getEmailList() called, then update live data`() {
        //Setting how up the mock behaves
        model =
            EmailListingViewModel(dataManager, CompositeDisposable(), MockScheduleProviderImpl())
        `when`(dataManager.getEmailList())
            .thenReturn(
                Single.error(
                    Exception("Something Went Wrong")
                )
            )
        //Fire the test method
        model.getEmailList()
        //Check that our live data is updated
        Assert.assertEquals("Something Went Wrong", model.responseListData.value!!.errorMessage)
    }
}