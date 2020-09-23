package com.humam.my_stories

import ImageGetter
import QuoteSpanClass
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.QuoteSpan
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_story_opend.*


class Story_opend : AppCompatActivity() {

    var fontsize=16f;
    lateinit var mAdView : AdView
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        setContentView(R.layout.activity_story_opend)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        val adView = AdView(this)


        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView2)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)




        val bundle = intent.extras
        if (bundle != null) {



            //id = bundle.getString("UpdateNoteId")

           // story_content.setText(bundle.getString("UpdateNoteTitle"))

//            story_content.setText(bundle.getString("UpdateNoteContent")?.let {
//
//                HtmlCompat.fromHtml( it,0) }).

           var title= title_txt.setText(bundle.getString("UpdateNoteTitle"))


                like_btn.setOnClickListener{



                     try {
                        packageManager.getPackageInfo("com.facebook.katana", 0)
                       val asas= Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/402914677195010"))
                         startActivity(asas)
                    } catch (e: Exception) {
                       val asas= Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/402914677195010"))
                         startActivity(asas)
                    }

                }

            bundle.getString("UpdateNoteContent")?.let { displayHtml(it) }
            share_btn.setOnClickListener {
                val intent= Intent()
                intent.action=Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT,"انا قرات قصة ${bundle.getString("UpdateNoteTitle")} على تطبيق قصص غموض ")
                intent.type="text/plain"
                startActivity(Intent.createChooser(intent,"Share To:"))
            }


           // story_content.setText(bundle.getString("UpdateNoteTitle"))
        }

    }


    fun increase_Font(view: View) {
        if (fontsize<=23f) {
            fontsize += 0.5f
            story_content.setTextSize(fontsize)
        }

    }
    fun decrease_Font(view: View) {
        if (fontsize>=14f) {
            fontsize -= 0.5f
            story_content.setTextSize(fontsize)
        }

    }


    private fun displayHtml(html: String) {

        val imageGetter = ImageGetter(resources, story_content)

        // Using Html framework to parse html
        val styledText =
            HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY, imageGetter,null)

        replaceQuoteSpans(styledText as Spannable)

        // setting the text after formatting html and downloadig and setting images
        story_content.text = styledText

        // to enable image/link clicking
        story_content.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun replaceQuoteSpans(spannable: Spannable)
    {
        val quoteSpans: Array<QuoteSpan> =
            spannable.getSpans(0, spannable.length - 1, QuoteSpan::class.java)

        for (quoteSpan in quoteSpans)
        {
            val start: Int = spannable.getSpanStart(quoteSpan)
            val end: Int = spannable.getSpanEnd(quoteSpan)
            val flags: Int = spannable.getSpanFlags(quoteSpan)
            spannable.removeSpan(quoteSpan)
            spannable.setSpan(
                QuoteSpanClass(
                    // background color
                    ContextCompat.getColor(this, R.color.colorPrimary),
                    // strip color
                    ContextCompat.getColor(this, R.color.colorAccent),
                    // strip width
                    10F, 50F
                ),
                start, end, flags
            )
        }
    }



}

