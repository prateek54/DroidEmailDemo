package com.droidiot.demoProject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.droidiot.demoProject.data.DataManager
import com.droidiot.demoProject.data.model.EmailModel
import com.droidiot.demoProject.data.model.SenderModel
import com.droidiot.demoProject.data.network.ApiHelper
import com.droidiot.demoProject.ui.emailListing.EmailListingViewModel
import com.droidiot.demoProject.util.RxSchedulersOverrideRule
import com.droidiot.demoProject.utils.ScheduleProviderImpl
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
        model =
            EmailListingViewModel(dataManager, CompositeDisposable(), ScheduleProviderImpl())
    }


    @Test
    fun `Given DataManager returns Empty data, when getEmailList() called, then update live data`() {
        //Setting how up the mock behaves
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

    @Test
    fun `Given DataManager returns some data, when getEmailListFiltered() called with some query text, then update live data`() {
        //Fire the test method
        val e = EmailModel("1","Want to buy this course","Offer","12:00 pm", SenderModel("Prateek","Batra","url"))
        `when`(dataManager.getEmailList())
            .thenReturn(
                Single.just(arrayListOf(e))
            )
        //Fire the test method
        model.getEmailList()
        model.getEmailListFiltered("prateek")
        //Check that our live data is updated
        Assert.assertEquals("Record with above query found",e,model.filterListData.value!!.data?.get(0))
    }

    @Test
    fun `Given DataManager returns whole data, when getEmailListFiltered() called with empty query text, then update live data`() {
        //Fire the test method
        val e = EmailModel("1","Want to buy this course","Offer","12:00 pm", SenderModel("Prateek","Batra","url"))
        `when`(dataManager.getEmailList())
            .thenReturn(
                Single.just(arrayListOf(e))
            )
        //Fire the test method
        model.getEmailList()
        model.getEmailListFiltered("")
        //Check that our live data is updated
        Assert.assertEquals("All records found",
            model.filterListData.value!!.data?.size,model.filterListData.value!!.data?.size)
    }

    @Test
    fun `Given DataManager returns empty list, when getEmailListFiltered() called with wrong query text, then update live data`() {
        //Fire the test method
        val e = EmailModel("1","Want to buy this course","Offer","12:00 pm", SenderModel("Prateek","Batra","url"))
        `when`(dataManager.getEmailList())
            .thenReturn(
                Single.just(arrayListOf(e))
            )
        //Fire the test method
        model.getEmailList()
        model.getEmailListFiltered("WrongQuery")
        //Check that our live data is updated
        Assert.assertEquals("No records found",
            0,model.filterListData.value!!.data?.size)
    }
}