package com.hubofallthings.datatrader.manager

import android.app.Activity
import android.content.Context
import android.widget.ProgressBar
import android.widget.TextView
import com.hubofallthings.android.hatApi.objects.datadebits.HATDataDebitValuesObject
import com.hubofallthings.android.hatApi.objects.dataoffers.DataOfferObject
import com.hubofallthings.datatrader.R
import com.hubofallthings.datatrader.activity.OfferDetailsActivity

enum class DataOfferStatusManager{
    Accepted,Completed,Available;

    fun getState(dataOffer: DataOfferObject) : DataOfferStatusManager{
        return when(dataOffer.claim?.claimStatus){
            "completed"-> Completed
            "claimed"-> Accepted
            else-> Available
        }
    }
    fun setupProgressBar(offer : DataOfferObject, dataDebitValue : HATDataDebitValuesObject?, progressBar : ProgressBar , progressText : TextView,  context : Context) {
        val state = getState(offer)
        when (state) {
            Accepted -> {
                if (dataDebitValue == null) return
                progressBar.progress = 0
                progressText.text = context.getString(R.string.checking_status)

                val ar =  dataDebitValue.conditions?.toArray()  as Array<Boolean>
                val v =ar.reduce {a,b-> a && b}

                if(v){
                    progressBar.progress = 2
                    progressText.text = context.getString(R.string.fetching_2_3)
                }else{
                    progressBar.progress = 1
                    progressText.text =  context.getString(R.string.offer_accepted)
                }
                if(!dataDebitValue.bundle.isNullOrEmpty()){
                    progressBar.progress = 2
                    progressText.text = context.getString(R.string.fetching_2_3)
                } else {
                    progressBar.progress = 0
                    progressText.text = context.getString(R.string.problem_claiming_offer)
                }
            }
            Completed -> {
                progressBar.progress = 3
                progressText.text = context.getString(R.string.offer_completed)
            }
            Available -> {
                progressBar.progress = 1
                progressText.text = context.getString(R.string.offer_accepted)
            }
        }
    }
}