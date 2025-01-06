package tw.music.streamer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class AboutActivity extends AppCompatActivity {

    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();

    private ArrayList<HashMap<String, Object>> theme_map = new ArrayList<>();

    private LinearLayout linear1;
    private ScrollView vscroll1;
    private ImageView imageview1;
    private TextView textview1;
    private LinearLayout linear2;
    private LinearLayout linear3;
    private LinearLayout linear11;
    private LinearLayout linear24;
    private LinearLayout linear5;
    private LinearLayout linear6;
    private LinearLayout linear7;
    private LinearLayout linear46;
    private LinearLayout linear9;
    private LinearLayout linear26;
    private ImageView imageview2;
    private LinearLayout linear25;
    private LinearLayout linear4;
    private TextView textview24;
    private TextView textview11;
    private TextView textview2;
    private ImageView imageview3;
    private LinearLayout linear8;
    private TextView textview3;
    private TextView textview4;
    private ImageView imageview4;
    private LinearLayout linear10;
    private TextView textview5;
    private TextView textview6;
    private ImageView imageview22;
    private LinearLayout linear47;
    private TextView textview45;
    private TextView textview46;
    private ImageView imageview5;
    private TextView textview12;
    private ImageView imageview12;
    private LinearLayout linear27;
    private TextView textview25;
    private TextView textview26;
    private TextView textview7;
    private LinearLayout linear12;
    private LinearLayout linear14;
    private LinearLayout linear40;
    private LinearLayout linear30;
    private LinearLayout linear16;
    private LinearLayout linear22;
    private LinearLayout linear42;
    private LinearLayout linear44;
    private LinearLayout linear36;
    private ImageView imageview6;
    private LinearLayout linear13;
    private TextView textview14;
    private TextView textview13;
    private ImageView imageview7;
    private LinearLayout linear15;
    private TextView textview15;
    private TextView textview16;
    private ImageView imageview19;
    private LinearLayout linear41;
    private TextView textview39;
    private TextView textview40;
    private ImageView imageview14;
    private LinearLayout linear31;
    private TextView textview29;
    private TextView textview30;
    private ImageView imageview8;
    private LinearLayout linear17;
    private TextView textview17;
    private TextView textview18;
    private ImageView imageview11;
    private LinearLayout linear23;
    private TextView textview23;
    private TextView textview8;
    private ImageView imageview20;
    private LinearLayout linear43;
    private TextView textview41;
    private TextView textview42;
    private ImageView imageview21;
    private LinearLayout linear45;
    private TextView textview43;
    private TextView textview44;
    private ImageView imageview17;
    private LinearLayout linear37;
    private TextView textview35;
    private TextView textview36;
    private TextView textview9;
    private LinearLayout linear28;
    private LinearLayout linear32;
    private LinearLayout linear34;
    private LinearLayout linear38;
    private LinearLayout linear18;
    private LinearLayout linear20;
    private ImageView imageview13;
    private LinearLayout linear29;
    private TextView textview27;
    private TextView textview28;
    private ImageView imageview15;
    private LinearLayout linear33;
    private TextView textview31;
    private TextView textview32;
    private ImageView imageview16;
    private LinearLayout linear35;
    private TextView textview33;
    private TextView textview34;
    private ImageView imageview18;
    private LinearLayout linear39;
    private TextView textview37;
    private TextView textview38;
    private ImageView imageview9;
    private LinearLayout linear19;
    private TextView textview20;
    private TextView textview19;
    private ImageView imageview10;
    private LinearLayout linear21;
    private TextView textview21;
    private TextView textview22;

    private SharedPreferences data;
    private Intent intentlol = new Intent();
    private Intent dataintent = new Intent();
    private AlertDialog.Builder dialog;
    private Intent intentextra = new Intent();
    private DatabaseReference profile = _firebase.getReference("profile/text");
    private ChildEventListener _profile_child_listener;
    private DatabaseReference prof_img = _firebase.getReference("profile/image");
    private ChildEventListener _prof_img_child_listener;
    private double _easterEgg;
    private double _easterEgg2;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.about);
        com.google.firebase.FirebaseApp.initializeApp(this);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {

        linear1 = findViewById(R.id.linear1);
        vscroll1 = findViewById(R.id.vscroll1);
        imageview1 = findViewById(R.id.imageview1);
        textview1 = findViewById(R.id.textview1);
        linear2 = findViewById(R.id.linear2);
        linear3 = findViewById(R.id.linear3);
        linear11 = findViewById(R.id.linear11);
        linear24 = findViewById(R.id.linear24);
        linear5 = findViewById(R.id.linear5);
        linear6 = findViewById(R.id.linear6);
        linear7 = findViewById(R.id.linear7);
        linear46 = findViewById(R.id.linear46);
        linear9 = findViewById(R.id.linear9);
        linear26 = findViewById(R.id.linear26);
        imageview2 = findViewById(R.id.imageview2);
        linear25 = findViewById(R.id.linear25);
        linear4 = findViewById(R.id.linear4);
        textview24 = findViewById(R.id.textview24);
        textview11 = findViewById(R.id.textview11);
        textview2 = findViewById(R.id.textview2);
        imageview3 = findViewById(R.id.imageview3);
        linear8 = findViewById(R.id.linear8);
        textview3 = findViewById(R.id.textview3);
        textview4 = findViewById(R.id.textview4);
        imageview4 = findViewById(R.id.imageview4);
        linear10 = findViewById(R.id.linear10);
        textview5 = findViewById(R.id.textview5);
        textview6 = findViewById(R.id.textview6);
        imageview22 = findViewById(R.id.imageview22);
        linear47 = findViewById(R.id.linear47);
        textview45 = findViewById(R.id.textview45);
        textview46 = findViewById(R.id.textview46);
        imageview5 = findViewById(R.id.imageview5);
        textview12 = findViewById(R.id.textview12);
        imageview12 = findViewById(R.id.imageview12);
        linear27 = findViewById(R.id.linear27);
        textview25 = findViewById(R.id.textview25);
        textview26 = findViewById(R.id.textview26);
        textview7 = findViewById(R.id.textview7);
        linear12 = findViewById(R.id.linear12);
        linear14 = findViewById(R.id.linear14);
        linear40 = findViewById(R.id.linear40);
        linear30 = findViewById(R.id.linear30);
        linear16 = findViewById(R.id.linear16);
        linear22 = findViewById(R.id.linear22);
        linear42 = findViewById(R.id.linear42);
        linear44 = findViewById(R.id.linear44);
        linear36 = findViewById(R.id.linear36);
        imageview6 = findViewById(R.id.imageview6);
        linear13 = findViewById(R.id.linear13);
        textview14 = findViewById(R.id.textview14);
        textview13 = findViewById(R.id.textview13);
        imageview7 = findViewById(R.id.imageview7);
        linear15 = findViewById(R.id.linear15);
        textview15 = findViewById(R.id.textview15);
        textview16 = findViewById(R.id.textview16);
        imageview19 = findViewById(R.id.imageview19);
        linear41 = findViewById(R.id.linear41);
        textview39 = findViewById(R.id.textview39);
        textview40 = findViewById(R.id.textview40);
        imageview14 = findViewById(R.id.imageview14);
        linear31 = findViewById(R.id.linear31);
        textview29 = findViewById(R.id.textview29);
        textview30 = findViewById(R.id.textview30);
        imageview8 = findViewById(R.id.imageview8);
        linear17 = findViewById(R.id.linear17);
        textview17 = findViewById(R.id.textview17);
        textview18 = findViewById(R.id.textview18);
        imageview11 = findViewById(R.id.imageview11);
        linear23 = findViewById(R.id.linear23);
        textview23 = findViewById(R.id.textview23);
        textview8 = findViewById(R.id.textview8);
        imageview20 = findViewById(R.id.imageview20);
        linear43 = findViewById(R.id.linear43);
        textview41 = findViewById(R.id.textview41);
        textview42 = findViewById(R.id.textview42);
        imageview21 = findViewById(R.id.imageview21);
        linear45 = findViewById(R.id.linear45);
        textview43 = findViewById(R.id.textview43);
        textview44 = findViewById(R.id.textview44);
        imageview17 = findViewById(R.id.imageview17);
        linear37 = findViewById(R.id.linear37);
        textview35 = findViewById(R.id.textview35);
        textview36 = findViewById(R.id.textview36);
        textview9 = findViewById(R.id.textview9);
        linear28 = findViewById(R.id.linear28);
        linear32 = findViewById(R.id.linear32);
        linear34 = findViewById(R.id.linear34);
        linear38 = findViewById(R.id.linear38);
        linear18 = findViewById(R.id.linear18);
        linear20 = findViewById(R.id.linear20);
        imageview13 = findViewById(R.id.imageview13);
        linear29 = findViewById(R.id.linear29);
        textview27 = findViewById(R.id.textview27);
        textview28 = findViewById(R.id.textview28);
        imageview15 = findViewById(R.id.imageview15);
        linear33 = findViewById(R.id.linear33);
        textview31 = findViewById(R.id.textview31);
        textview32 = findViewById(R.id.textview32);
        imageview16 = findViewById(R.id.imageview16);
        linear35 = findViewById(R.id.linear35);
        textview33 = findViewById(R.id.textview33);
        textview34 = findViewById(R.id.textview34);
        imageview18 = findViewById(R.id.imageview18);
        linear39 = findViewById(R.id.linear39);
        textview37 = findViewById(R.id.textview37);
        textview38 = findViewById(R.id.textview38);
        imageview9 = findViewById(R.id.imageview9);
        linear19 = findViewById(R.id.linear19);
        textview20 = findViewById(R.id.textview20);
        textview19 = findViewById(R.id.textview19);
        imageview10 = findViewById(R.id.imageview10);
        linear21 = findViewById(R.id.linear21);
        textview21 = findViewById(R.id.textview21);
        textview22 = findViewById(R.id.textview22);
        data = getSharedPreferences("teamdata", Activity.MODE_PRIVATE);
        dialog = new AlertDialog.Builder(this);

        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                finish();
            }
        });

        linear5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                dataintent.setData(Uri.parse("https://zrytezene.xyz"));
                dataintent.setAction(Intent.ACTION_VIEW);
                startActivity(dataintent);
            }
        });

        linear6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (!(_easterEgg > 9)) {
                    _easterEgg++;
                }
            }
        });

        linear7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (!(_easterEgg2 > 9)) {
                    _easterEgg2++;
                }
            }
        });

        linear46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

            }
        });

        linear9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                dialog.setCancelable(false);
                dialog.setTitle("ZryteZene");
                dialog.setMessage("MIT License\nLast updated: 2019-10-05\n\nCopyright © 2017 ZryteZene All rights reserved.\n\nRedistribution and use in source and binary forms, with or without modification,\nare permitted provided that the following conditions are met:\n* Redistributions of source code must retain the above copyright notice, this\n  list of conditions and the following disclaimer.\n* Redistributions in binary form must reproduce the above copyright notice, this\n  list of conditions and the following disclaimer in the documentation and/or\n  other materials provided with the distribution.\n* Neither the name of ZryteZene nor the names of its contributors may be used to\n  endorse or promote products derived from this software without specific prior\n  written permission.\n\nTHIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS “AS IS” AND\nANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED\nWARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE\nDISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR\nANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES\n(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;\nLOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON\nANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT\n(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS\nSOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n\n\nTERMS AND CONDITIONS\nLast updated: 2020-04-05\n\n1. Introduction\n\nWelcome to Zrytezene (“Company”, “we”, “our”, “us”)!\n\nThese Terms of Service (“Terms”, “Terms of Service”) govern your use of our website located at https://zrytezene.web.app (together or individually “Service”) operated by ZryteZene.\n\nOur Privacy Policy also governs your use of our Service and explains how we collect, safeguard and disclose information that results from your use of our web pages.\n\nYour agreement with us includes these Terms and our Privacy Policy (“Agreements”). You acknowledge that you have read and understood Agreements, and agree to be bound of them.\n\nIf you do not agree with (or cannot comply with) Agreements, then you may not use the Service, but please let us know by emailing at zrytezene@gmail.com so we can try to find a solution. These Terms apply to all visitors, users and others who wish to access or use Service.\n\n2. Communications\n\nBy using our Service, you agree to subscribe to newsletters, marketing or promotional materials and other information we may send. However, you may opt out of receiving any, or all, of these communications from us by following the unsubscribe link or by emailing at zrytezene@gmail.com.\n\n3. Contests, Sweepstakes and Promotions\n\nAny contests, sweepstakes or other promotions (collectively, “Promotions”) made available through Service may be governed by rules that are separate from these Terms of Service. If you participate in any Promotions, please review the applicable rules as well as our Privacy Policy. If the rules for a Promotion conflict with these Terms of Service, Promotion rules will apply.\n\n4. Content\n\nOur Service allows you to post, link, store, share and otherwise make available certain information, text, graphics, videos, or other material (“Content”). You are responsible for Content that you post on or through Service, including its legality, reliability, and appropriateness.\n\nBy posting Content on or through Service, You represent and warrant that: (i) Content is yours (you own it) and/or you have the right to use it and the right to grant us the rights and license as provided in these Terms, and (ii) that the posting of your Content on or through Service does not violate the privacy rights, publicity rights, copyrights, contract rights or any other rights of any person or entity. We reserve the right to terminate the account of anyone found to be infringing on a copyright.\n\nYou retain any and all of your rights to any Content you submit, post or display on or through Service and you are responsible for protecting those rights. We take no responsibility and assume no liability for Content you or any third party posts on or through Service. However, by posting Content using Service you grant us the right and license to use, modify, publicly perform, publicly display, reproduce, and distribute such Content on and through Service. You agree that this license includes the right for us to make your Content available to other users of Service, who may also use your Content subject to these Terms.\n\nZryteZene has the right but not the obligation to monitor and edit all Content provided by users.\n\nIn addition, Content found on or through this Service are the property of ZryteZene or used with permission. You may not distribute, modify, transmit, reuse, download, repost, copy, or use said Content, whether in whole or in part, for commercial purposes or for personal gain, without express advance written permission from us.\n\n5. Prohibited Uses\n\nYou may use Service only for lawful purposes and in accordance with Terms. You agree not to use Service:\n\n0.1. In any way that violates any applicable national or international law or regulation.\n\n0.2. For the purpose of exploiting, harming, or attempting to exploit or harm minors in any way by exposing them to inappropriate content or otherwise.\n\n0.3. To transmit, or procure the sending of, any advertising or promotional material, including any “junk mail”, “chain letter,” “spam,” or any other similar solicitation.\n\n0.4. To impersonate or attempt to impersonate Company, a Company employee, another user, or any other person or entity.\n\n0.5. In any way that infringes upon the rights of others, or in any way is illegal, threatening, fraudulent, or harmful, or in connection with any unlawful, illegal, fraudulent, or harmful purpose or activity.\n\n0.6. To engage in any other conduct that restricts or inhibits anyone’s use or enjoyment of Service, or which, as determined by us, may harm or offend Company or users of Service or expose them to liability.\n\nAdditionally, you agree not to:\n\n0.1. Use Service in any manner that could disable, overburden, damage, or impair Service or interfere with any other party’s use of Service, including their ability to engage in real time activities through Service.\n\n0.2. Use any robot, spider, or other automatic device, process, or means to access Service for any purpose, including monitoring or copying any of the material on Service.\n\n0.3. Use any manual process to monitor or copy any of the material on Service or for any other unauthorized purpose without our prior written consent.\n\n0.4. Use any device, software, or routine that interferes with the proper working of Service.\n\n0.5. Introduce any viruses, trojan horses, worms, logic bombs, or other material which is malicious or technologically harmful.\n\n0.6. Attempt to gain unauthorized access to, interfere with, damage, or disrupt any parts of Service, the server on which Service is stored, or any server, computer, or database connected to Service.\n\n0.7. Attack Service via a denial-of-service attack or a distributed denial-of-service attack.\n\n0.8. Take any action that may damage or falsify Company rating.\n\n0.9. Otherwise attempt to interfere with the proper working of Service.\n\n6. Analytics\n\nWe may use third-party Service Providers to monitor and analyze the use of our Service.\n\n7. No Use By Minors\n\nService is intended only for access and use by individuals at least eighteen (18) years old. By accessing or using Service, you warrant and represent that you are at least eighteen (18) years of age and with the full authority, right, and capacity to enter into this agreement and abide by all of the terms and conditions of Terms. If you are not at least eighteen (18) years old, you are prohibited from both the access and usage of Service.\n\n8. Accounts\n\nWhen you create an account with us, you guarantee that you are above the age of 18, and that the information you provide us is accurate, complete, and current at all times. Inaccurate, incomplete, or obsolete information may result in the immediate termination of your account on Service.\n\nYou are responsible for maintaining the confidentiality of your account and password, including but not limited to the restriction of access to your computer and/or account. You agree to accept responsibility for any and all activities or actions that occur under your account and/or password, whether your password is with our Service or a third-party service. You must notify us immediately upon becoming aware of any breach of security or unauthorized use of your account.\n\nYou may not use as a username the name of another person or entity or that is not lawfully available for use, a name or trademark that is subject to any rights of another person or entity other than you, without appropriate authorization. You may not use as a username any name that is offensive, vulgar or obscene.\n\nWe reserve the right to refuse service, terminate accounts, remove or edit content, or cancel orders in our sole discretion.\n\n9. Intellectual Property\n\nService and its original content (excluding Content provided by users), features and functionality are and will remain the exclusive property of ZryteZene and its licensors. Service is protected by copyright, trademark, and other laws of and foreign countries. Our trademarks may not be used in connection with any product or service without the prior written consent of ZryteZene.\n\n10. Copyright Policy\n\nWe respect the intellectual property rights of others. It is our policy to respond to any claim that Content posted on Service infringes on the copyright or other intellectual property rights (“Infringement”) of any person or entity.\n\nIf you are a copyright owner, or authorized on behalf of one, and you believe that the copyrighted work has been copied in a way that constitutes copyright infringement, please submit your claim via email to zrytezene@gmail.com, with the subject line: “Copyright Infringement” and include in your claim a detailed description of the alleged Infringement as detailed below, under “DMCA Notice and Procedure for Copyright Infringement Claims”\n\nYou may be held accountable for damages (including costs and attorneys’ fees) for misrepresentation or bad-faith claims on the infringement of any Content found on and/or through Service on your copyright.\n\n11. DMCA Notice and Procedure for Copyright Infringement Claims\n\nYou may submit a notification pursuant to the Digital Millennium Copyright Act (DMCA) by providing our Copyright Agent with the following information in writing (see 17 U.S.C 512(c)(3) for further detail):\n\n0.1. an electronic or physical signature of the person authorized to act on behalf of the owner of the copyright’s interest;\n\n0.2. a description of the copyrighted work that you claim has been infringed, including the URL (i.e., web page address) of the location where the copyrighted work exists or a copy of the copyrighted work;\n\n0.3. identification of the URL or other specific location on Service where the material that you claim is infringing is located;\n\n0.4. your address, telephone number, and email address;\n\n0.5. a statement by you that you have a good faith belief that the disputed use is not authorized by the copyright owner, its agent, or the law;\n\n0.6. a statement by you, made under penalty of perjury, that the above information in your notice is accurate and that you are the copyright owner or authorized to act on the copyright owner’s behalf.\n\nYou can contact our Copyright Agent via email at zrytezene@gmail.com.\n\n12. Error Reporting and Feedback\n\nYou may provide us either directly at zrytezene@gmail.com or via third party sites and tools with information and feedback concerning errors, suggestions for improvements, ideas, problems, complaints, and other matters related to our Service (“Feedback”). You acknowledge and agree that: (i) you shall not retain, acquire or assert any intellectual property right or other right, title or interest in or to the Feedback; (ii) Company may have development ideas similar to the Feedback; (iii) Feedback does not contain confidential information or proprietary information from you or any third party; and (iv) Company is not under any obligation of confidentiality with respect to the Feedback. In the event the transfer of the ownership to the Feedback is not possible due to applicable mandatory laws, you grant Company and its affiliates an exclusive, transferable, irrevocable, free-of-charge, sub-licensable, unlimited and perpetual right to use (including copy, modify, create derivative works, publish, distribute and commercialize) Feedback in any manner and for any purpose.\n\n13. Links To Other Web Sites\n\nOur Service may contain links to third party web sites or services that are not owned or controlled by ZryteZene.\n\nZryteZene has no control over, and assumes no responsibility for the content, privacy policies, or practices of any third party web sites or services. We do not warrant the offerings of any of these entities/individuals or their websites.\n\nFor example, the outlined Terms of Service have been created using PolicyMaker.io, a free web application for generating high-quality legal documents. PolicyMaker’s free Terms and Conditions generator is an easy-to-use free tool for creating an excellent Terms of Service template for a website, blog, online store or app.\n\nYOU ACKNOWLEDGE AND AGREE THAT COMPANY SHALL NOT BE RESPONSIBLE OR LIABLE, DIRECTLY OR INDIRECTLY, FOR ANY DAMAGE OR LOSS CAUSED OR ALLEGED TO BE CAUSED BY OR IN CONNECTION WITH USE OF OR RELIANCE ON ANY SUCH CONTENT, GOODS OR SERVICES AVAILABLE ON OR THROUGH ANY SUCH THIRD PARTY WEB SITES OR SERVICES.\n\nWE STRONGLY ADVISE YOU TO READ THE TERMS OF SERVICE AND PRIVACY POLICIES OF ANY THIRD PARTY WEB SITES OR SERVICES THAT YOU VISIT.\n\n14. Disclaimer Of Warranty\n\nTHESE SERVICES ARE PROVIDED BY COMPANY ON AN “AS IS” AND “AS AVAILABLE” BASIS. COMPANY MAKES NO REPRESENTATIONS OR WARRANTIES OF ANY KIND, EXPRESS OR IMPLIED, AS TO THE OPERATION OF THEIR SERVICES, OR THE INFORMATION, CONTENT OR MATERIALS INCLUDED THEREIN. YOU EXPRESSLY AGREE THAT YOUR USE OF THESE SERVICES, THEIR CONTENT, AND ANY SERVICES OR ITEMS OBTAINED FROM US IS AT YOUR SOLE RISK.\n\nNEITHER COMPANY NOR ANY PERSON ASSOCIATED WITH COMPANY MAKES ANY WARRANTY OR REPRESENTATION WITH RESPECT TO THE COMPLETENESS, SECURITY, RELIABILITY, QUALITY, ACCURACY, OR AVAILABILITY OF THE SERVICES. WITHOUT LIMITING THE FOREGOING, NEITHER COMPANY NOR ANYONE ASSOCIATED WITH COMPANY REPRESENTS OR WARRANTS THAT THE SERVICES, THEIR CONTENT, OR ANY SERVICES OR ITEMS OBTAINED THROUGH THE SERVICES WILL BE ACCURATE, RELIABLE, ERROR-FREE, OR UNINTERRUPTED, THAT DEFECTS WILL BE CORRECTED, THAT THE SERVICES OR THE SERVER THAT MAKES IT AVAILABLE ARE FREE OF VIRUSES OR OTHER HARMFUL COMPONENTS OR THAT THE SERVICES OR ANY SERVICES OR ITEMS OBTAINED THROUGH THE SERVICES WILL OTHERWISE MEET YOUR NEEDS OR EXPECTATIONS.\n\nCOMPANY HEREBY DISCLAIMS ALL WARRANTIES OF ANY KIND, WHETHER EXPRESS OR IMPLIED, STATUTORY, OR OTHERWISE, INCLUDING BUT NOT LIMITED TO ANY WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT, AND FITNESS FOR PARTICULAR PURPOSE.\n\nTHE FOREGOING DOES NOT AFFECT ANY WARRANTIES WHICH CANNOT BE EXCLUDED OR LIMITED UNDER APPLICABLE LAW.\n\n15. Limitation Of Liability\n\nEXCEPT AS PROHIBITED BY LAW, YOU WILL HOLD US AND OUR OFFICERS, DIRECTORS, EMPLOYEES, AND AGENTS HARMLESS FOR ANY INDIRECT, PUNITIVE, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGE, HOWEVER IT ARISES (INCLUDING ATTORNEYS’ FEES AND ALL RELATED COSTS AND EXPENSES OF LITIGATION AND ARBITRATION, OR AT TRIAL OR ON APPEAL, IF ANY, WHETHER OR NOT LITIGATION OR ARBITRATION IS INSTITUTED), WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE, OR OTHER TORTIOUS ACTION, OR ARISING OUT OF OR IN CONNECTION WITH THIS AGREEMENT, INCLUDING WITHOUT LIMITATION ANY CLAIM FOR PERSONAL INJURY OR PROPERTY DAMAGE, ARISING FROM THIS AGREEMENT AND ANY VIOLATION BY YOU OF ANY FEDERAL, STATE, OR LOCAL LAWS, STATUTES, RULES, OR REGULATIONS, EVEN IF COMPANY HAS BEEN PREVIOUSLY ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. EXCEPT AS PROHIBITED BY LAW, IF THERE IS LIABILITY FOUND ON THE PART OF COMPANY, IT WILL BE LIMITED TO THE AMOUNT PAID FOR THE PRODUCTS AND/OR SERVICES, AND UNDER NO CIRCUMSTANCES WILL THERE BE CONSEQUENTIAL OR PUNITIVE DAMAGES. SOME STATES DO NOT ALLOW THE EXCLUSION OR LIMITATION OF PUNITIVE, INCIDENTAL OR CONSEQUENTIAL DAMAGES, SO THE PRIOR LIMITATION OR EXCLUSION MAY NOT APPLY TO YOU.\n\n16. Termination\n\nWe may terminate or suspend your account and bar access to Service immediately, without prior notice or liability, under our sole discretion, for any reason whatsoever and without limitation, including but not limited to a breach of Terms.\n\nIf you wish to terminate your account, you may simply discontinue using Service.\n\nAll provisions of Terms which by their nature should survive termination shall survive termination, including, without limitation, ownership provisions, warranty disclaimers, indemnity and limitations of liability.\n\n17. Governing Law\n\nThese Terms shall be governed and construed in accordance with the laws of Indonesia, which governing law applies to agreement without regard to its conflict of law provisions.\n\nOur failure to enforce any right or provision of these Terms will not be considered a waiver of those rights. If any provision of these Terms is held to be invalid or unenforceable by a court, the remaining provisions of these Terms will remain in effect. These Terms constitute the entire agreement between us regarding our Service and supersede and replace any prior agreements we might have had between us regarding Service.\n\n18. Changes To Service\n\nWe reserve the right to withdraw or amend our Service, and any service or material we provide via Service, in our sole discretion without notice. We will not be liable if for any reason all or any part of Service is unavailable at any time or for any period. From time to time, we may restrict access to some parts of Service, or the entire Service, to users, including registered users.\n\n19. Amendments To Terms\n\nWe may amend Terms at any time by posting the amended terms on this site. It is your responsibility to review these Terms periodically.\n\nYour continued use of the Platform following the posting of revised Terms means that you accept and agree to the changes. You are expected to check this page frequently so you are aware of any changes, as they are binding on you.\n\nBy continuing to access or use our Service after any revisions become effective, you agree to be bound by the revised terms. If you do not agree to the new terms, you are no longer authorized to use Service.\n\n20. Waiver And Severability\n\nNo waiver by Company of any term or condition set forth in Terms shall be deemed a further or continuing waiver of such term or condition or a waiver of any other term or condition, and any failure of Company to assert a right or provision under Terms shall not constitute a waiver of such right or provision.\n\nIf any provision of Terms is held by a court or other tribunal of competent jurisdiction to be invalid, illegal or unenforceable for any reason, such provision shall be eliminated or limited to the minimum extent such that the remaining provisions of Terms will continue in full force and effect.\n\n21. Acknowledgement\n\nBY USING SERVICE OR OTHER SERVICES PROVIDED BY US, YOU ACKNOWLEDGE THAT YOU HAVE READ THESE TERMS OF SERVICE AND AGREE TO BE BOUND BY THEM.\n\n22. Contact Us\n\nPlease send your feedback, comments, requests for technical support by email: zrytezene@gmail.com.\n\n\nPRIVACY POLICY\nEffective date: 2020-04-05\n\n1. Introduction\n\nWelcome to Zrytezene.\n\nZryteZene (“us”, “we”, or “our”) operates https://zrytezene.web.app (hereinafter referred to as “Service”).\n\nOur Privacy Policy governs your visit to https://zrytezene.web.app, and explains how we collect, safeguard and disclose information that results from your use of our Service.\n\nWe use your data to provide and improve Service. By using Service, you agree to the collection and use of information in accordance with this policy. Unless otherwise defined in this Privacy Policy, the terms used in this Privacy Policy have the same meanings as in our Terms and Conditions.\n\nOur Terms and Conditions (“Terms”) govern all use of our Service and together with the Privacy Policy constitutes your agreement with us (“agreement”).\n\n2. Definitions\n\nSERVICE means the https://zrytezene.web.app website operated by ZryteZene.\n\nPERSONAL DATA means data about a living individual who can be identified from those data (or from those and other information either in our possession or likely to come into our possession).\n\nUSAGE DATA is data collected automatically either generated by the use of Service or from Service infrastructure itself (for example, the duration of a page visit).\n\nCOOKIES are small files stored on your device (computer or mobile device).\n\nDATA CONTROLLER means a natural or legal person who (either alone or jointly or in common with other persons) determines the purposes for which and the manner in which any personal data are, or are to be, processed. For the purpose of this Privacy Policy, we are a Data Controller of your data.\n\nDATA PROCESSORS (OR SERVICE PROVIDERS) means any natural or legal person who processes the data on behalf of the Data Controller. We may use the services of various Service Providers in order to process your data more effectively.\n\nDATA SUBJECT is any living individual who is the subject of Personal Data.\n\nTHE USER is the individual using our Service. The User corresponds to the Data Subject, who is the subject of Personal Data.\n\n3. Information Collection and Use\n\nWe collect several different types of information for various purposes to provide and improve our Service to you.\n\n4. Types of Data Collected\n\nPersonal Data\n\nWhile using our Service, we may ask you to provide us with certain personally identifiable information that can be used to contact or identify you (“Personal Data”). Personally identifiable information may include, but is not limited to:\n\n0.1. Email address\n\n0.2. First name and last name\n\n0.3. Phone number\n\n0.4. Address, Country, State, Province, ZIP/Postal code, City\n\n0.5. Cookies and Usage Data\n\nWe may use your Personal Data to contact you with newsletters, marketing or promotional materials and other information that may be of interest to you. You may opt out of receiving any, or all, of these communications from us by following the unsubscribe link.\n\nUsage Data\n\nWe may also collect information that your browser sends whenever you visit our Service or when you access Service by or through any device (“Usage Data”).\n\nThis Usage Data may include information such as your computer’s Internet Protocol address (e.g. IP address), browser type, browser version, the pages of our Service that you visit, the time and date of your visit, the time spent on those pages, unique device identifiers and other diagnostic data.\n\nWhen you access Service with a device, this Usage Data may include information such as the type of device you use, your device unique ID, the IP address of your device, your device operating system, the type of Internet browser you use, unique device identifiers and other diagnostic data.\n\nTracking Cookies Data\n\nWe use cookies and similar tracking technologies to track the activity on our Service and we hold certain information.\n\nCookies are files with a small amount of data which may include an anonymous unique identifier. Cookies are sent to your browser from a website and stored on your device. Other tracking technologies are also used such as beacons, tags and scripts to collect and track information and to improve and analyze our Service.\n\nYou can instruct your browser to refuse all cookies or to indicate when a cookie is being sent. However, if you do not accept cookies, you may not be able to use some portions of our Service.\n\nExamples of Cookies we use:\n\n0.1. Session Cookies: We use Session Cookies to operate our Service.\n\n0.2. Preference Cookies: We use Preference Cookies to remember your preferences and various settings.\n\n0.3. Security Cookies: We use Security Cookies for security purposes.\n\n0.4. Advertising Cookies: Advertising Cookies are used to serve you with advertisements that may be relevant to you and your interests.\n\nOther Data\n\nWhile using our Service, we may also collect the following information: sex, age, date of birth, place of birth, passport details, citizenship, registration at place of residence and actual address, telephone number (work, mobile), details of documents on education, qualification, professional training, employment agreements, non-disclosure agreements, information on bonuses and compensation, information on marital status, family members, social security (or other taxpayer identification) number, office location and other data.\n\n5. Use of Data\n\nZryteZene uses the collected data for various purposes:\n\n0.1. to provide and maintain our Service;\n\n0.2. to notify you about changes to our Service;\n\n0.3. to allow you to participate in interactive features of our Service when you choose to do so;\n\n0.4. to provide customer support;\n\n0.5. to gather analysis or valuable information so that we can improve our Service;\n\n0.6. to monitor the usage of our Service;\n\n0.7. to detect, prevent and address technical issues;\n\n0.8. to fulfil any other purpose for which you provide it;\n\n0.9. to carry out our obligations and enforce our rights arising from any contracts entered into between you and us, including for billing and collection;\n\n0.10. to provide you with notices about your account and/or subscription, including expiration and renewal notices, email-instructions, etc.;\n\n0.11. to provide you with news, special offers and general information about other goods, services and events which we offer that are similar to those that you have already purchased or enquired about unless you have opted not to receive such information;\n\n0.12. in any other way we may describe when you provide the information;\n\n0.13. for any other purpose with your consent.\n\n6. Retention of Data\n\nWe will retain your Personal Data only for as long as is necessary for the purposes set out in this Privacy Policy. We will retain and use your Personal Data to the extent necessary to comply with our legal obligations (for example, if we are required to retain your data to comply with applicable laws), resolve disputes, and enforce our legal agreements and policies.\n\nWe will also retain Usage Data for internal analysis purposes. Usage Data is generally retained for a shorter period, except when this data is used to strengthen the security or to improve the functionality of our Service, or we are legally obligated to retain this data for longer time periods.\n\n7. Transfer of Data\n\nYour information, including Personal Data, may be transferred to – and maintained on – computers located outside of your state, province, country or other governmental jurisdiction where the data protection laws may differ from those of your jurisdiction.\n\nIf you are located outside Indonesia and choose to provide information to us, please note that we transfer the data, including Personal Data, to Indonesia and process it there.\n\nYour consent to this Privacy Policy followed by your submission of such information represents your agreement to that transfer.\n\nZryteZene will take all the steps reasonably necessary to ensure that your data is treated securely and in accordance with this Privacy Policy and no transfer of your Personal Data will take place to an organisation or a country unless there are adequate controls in place including the security of your data and other personal information.\n\n8. Disclosure of Data\n\nWe may disclose personal information that we collect, or you provide:\n\n0.1. Disclosure for Law Enforcement.\n\nUnder certain circumstances, we may be required to disclose your Personal Data if required to do so by law or in response to valid requests by public authorities.\n\n0.2. Business Transaction.\n\nIf we or our subsidiaries are involved in a merger, acquisition or asset sale, your Personal Data may be transferred.\n\n0.3. Other cases. We may disclose your information also:\n\n0.3.1. to our subsidiaries and affiliates;\n\n0.3.2. to contractors, service providers, and other third parties we use to support our business;\n\n0.3.3. to fulfill the purpose for which you provide it;\n\n0.3.4. for the purpose of including your company’s logo on our website;\n\n0.3.5. for any other purpose disclosed by us when you provide the information;\n\n0.3.6. with your consent in any other cases;\n\n0.3.7. if we believe disclosure is necessary or appropriate to protect the rights, property, or safety of the Company, our customers, or others.\n\n9. Security of Data\n\nThe security of your data is important to us but remember that no method of transmission over the Internet or method of electronic storage is 100% secure. While we strive to use commercially acceptable means to protect your Personal Data, we cannot guarantee its absolute security.\n\n10. Service Providers\n\nWe may employ third party companies and individuals to facilitate our Service (“Service Providers”), provide Service on our behalf, perform Service-related services or assist us in analysing how our Service is used.\n\nThese third parties have access to your Personal Data only to perform these tasks on our behalf and are obligated not to disclose or use it for any other purpose.\n\n11. Analytics\n\nWe may use third-party Service Providers to monitor and analyze the use of our Service.\n\n12. CI/CD tools\n\nWe may use third-party Service Providers to automate the development process of our Service.\n\n13. Behavioral Remarketing\n\nWe may use remarketing services to advertise on third party websites to you after you visited our Service. We and our third-party vendors use cookies to inform, optimise and serve ads based on your past visits to our Service.\n\n14. Links to Other Sites\n\nOur Service may contain links to other sites that are not operated by us. If you click a third party link, you will be directed to that third party’s site. We strongly advise you to review the Privacy Policy of every site you visit.\n\nWe have no control over and assume no responsibility for the content, privacy policies or practices of any third party sites or services.\n\nFor example, the outlined Privacy Policy has been created using PolicyMaker.io, a free web application for generating high-quality legal documents. PolicyMaker’s online free privacy policy generator is an easy-to-use tool for creating privacy policy template for a website, blog, online store or app.\n\n15. Children’s Privacy\n\nOur Services are not intended for use by children under the age of 18 (“Child” or “Children”).\n\nWe do not knowingly collect personally identifiable information from Children under 18. If you become aware that a Child has provided us with Personal Data, please contact us. If we become aware that we have collected Personal Data from Children without verification of parental consent, we take steps to remove that information from our servers.\n\n16. Changes to This Privacy Policy\n\nWe may update our Privacy Policy from time to time. We will notify you of any changes by posting the new Privacy Policy on this page.\n\nWe will let you know via email and/or a prominent notice on our Service, prior to the change becoming effective and update “effective date” at the top of this Privacy Policy.\n\nYou are advised to review this Privacy Policy periodically for any changes. Changes to this Privacy Policy are effective when they are posted on this page.\n\n17. Contact Us\n\nIf you have any questions about this Privacy Policy, please contact us by email: zrytezene@gmail.com.\n\n\nDISCLAIMER\nLast updated: 2020-04-05\n\nWEBSITE DISCLAIMER\n\nThe information provided by ZryteZene (“Company”, “we”, “our”, “us”) on https://zrytezene.web.app (the “Site”) is for general informational purposes only. All information on the Site is provided in good faith, however we make no representation or warranty of any kind, express or implied, regarding the accuracy, adequacy, validity, reliability, availability, or completeness of any information on the Site.\n\nUNDER NO CIRCUMSTANCE SHALL WE HAVE ANY LIABILITY TO YOU FOR ANY LOSS OR DAMAGE OF ANY KIND INCURRED AS A RESULT OF THE USE OF THE SITE OR RELIANCE ON ANY INFORMATION PROVIDED ON THE SITE. YOUR USE OF THE SITE AND YOUR RELIANCE ON ANY INFORMATION ON THE SITE IS SOLELY AT YOUR OWN RISK.\n\nEXTERNAL LINKS DISCLAIMER\n\nThe Site may contain (or you may be sent through the Site) links to other websites or content belonging to or originating from third parties or links to websites and features. Such external links are not investigated, monitored, or checked for accuracy, adequacy, validity, reliability, availability or completeness by us.\n\nFor example, the outlined Disclaimer has been created using PolicyMaker.io, a free web application for generating high-quality legal documents. PolicyMaker’s free website Disclaimer generator is an easy-to-use tool for creating an excellent Disclaimer template for a website, blog, eCommerce store or app.\n\nWE DO NOT WARRANT, ENDORSE, GUARANTEE, OR ASSUME RESPONSIBILITY FOR THE ACCURACY OR RELIABILITY OF ANY INFORMATION OFFERED BY THIRD-PARTY WEBSITES LINKED THROUGH THE SITE OR ANY WEBSITE OR FEATURE LINKED IN ANY BANNER OR OTHER ADVERTISING. WE WILL NOT BE A PARTY TO OR IN ANY WAY BE RESPONSIBLE FOR MONITORING ANY TRANSACTION BETWEEN YOU AND THIRD-PARTY PROVIDERS OF PRODUCTS OR SERVICES.\n\nAFFILIATES DISCLAIMER\n\nThe Site may contain links to affiliate websites, and we may receive an affiliate commission for any purchases or actions made by you on the affiliate websites using such links.\n\nERRORS AND OMISSIONS DISCLAIMER\n\nWhile we have made every attempt to ensure that the information contained in this site has been obtained from reliable sources, ZryteZene is not responsible for any errors or omissions or for the results obtained from the use of this information. All information in this site is provided “as is”, with no guarantee of completeness, accuracy, timeliness or of the results obtained from the use of this information, and without warranty of any kind, express or implied, including, but not limited to warranties of performance, merchantability, and fitness for a particular purpose.\n\nIn no event will ZryteZene, its related partnerships or corporations, or the partners, agents or employees thereof be liable to you or anyone else for any decision made or action taken in reliance on the information in this Site or for any consequential, special or similar damages, even if advised of the possibility of such damages.\n\nGUEST CONTRIBUTORS DISCLAIMER\n\nThis Site may include content from guest contributors and any views or opinions expressed in such posts are personal and do not represent those of ZryteZene or any of its staff or affiliates unless explicitly stated.\n\nLOGOS AND TRADEMARKS DISCLAIMER\n\nAll logos and trademarks of third parties referenced on https://zrytezene.web.app are the trademarks and logos of their respective owners. Any inclusion of such trademarks or logos does not imply or constitute any approval, endorsement or sponsorship of ZryteZene by such owners.\n\nCONTACT US\n\nShould you have any feedback, comments, requests for technical support or other inquiries, please contact us by email: zrytezene@gmail.com.\n");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface _dialog, int _which) {

                    }
                });
                dialog.create().show();
            }
        });

        linear26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                dataintent.setData(Uri.parse("https://discord.gg/ERpx6dv"));
                dataintent.setAction(Intent.ACTION_VIEW);
                startActivity(dataintent);
            }
        });

        linear12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intentextra.setClass(getApplicationContext(), ProfileActivity.class);
                intentextra.putExtra("uid", "kGGvvEghj5XfVBuX1obf2hTrjoc2");
                startActivity(intentextra);
            }
        });

        linear14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intentextra.setClass(getApplicationContext(), ProfileActivity.class);
                intentextra.putExtra("uid", "XwzlFNWx8keGp1QaCHthA2QVbED2");
                startActivity(intentextra);
            }
        });

        linear40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intentextra.setClass(getApplicationContext(), ProfileActivity.class);
                intentextra.putExtra("uid", "UpQOFTwn76RAsKcntwPvi6P90KA2");
                startActivity(intentextra);
            }
        });

        linear30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intentextra.setClass(getApplicationContext(), ProfileActivity.class);
                intentextra.putExtra("uid", "tO70jxIr23V05U396Pck9SUUAKy2");
                startActivity(intentextra);
            }
        });

        linear16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intentextra.setClass(getApplicationContext(), ProfileActivity.class);
                intentextra.putExtra("uid", "1Vy4K7yIVRbhbdnUxcjmRLQ3v2t2");
                startActivity(intentextra);
            }
        });

        linear22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intentextra.setClass(getApplicationContext(), ProfileActivity.class);
                intentextra.putExtra("uid", "5FItT6oZk9PX67Z0yRspUVpghIK2");
                startActivity(intentextra);
            }
        });

        linear42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intentextra.setClass(getApplicationContext(), ProfileActivity.class);
                intentextra.putExtra("uid", "Juc0IA2zV8PgIaHnDLFThqf39lr2");
                startActivity(intentextra);
            }
        });

        linear44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intentextra.setClass(getApplicationContext(), ProfileActivity.class);
                intentextra.putExtra("uid", "v2uhfHvCAdSouilgGXHGBD5fdgA3");
                startActivity(intentextra);
            }
        });

        linear36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intentextra.setClass(getApplicationContext(), ProfileActivity.class);
                intentextra.putExtra("uid", "pi9jaUVRZHWsaMkekOukiH4jTiR2");
                startActivity(intentextra);
            }
        });

        linear28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intentextra.setClass(getApplicationContext(), ProfileActivity.class);
                intentextra.putExtra("uid", "nGBcLSDBxgdacBFCud9FNPKEMJD3");
                startActivity(intentextra);
            }
        });

        linear32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intentextra.setClass(getApplicationContext(), ProfileActivity.class);
                intentextra.putExtra("uid", "8i03R0bTjoRSEXYNmC60Spt5nse2");
                startActivity(intentextra);
            }
        });

        linear34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intentextra.setClass(getApplicationContext(), ProfileActivity.class);
                intentextra.putExtra("uid", "l9PruxPML6REkTNX9kkptMwsz2k2");
                startActivity(intentextra);
            }
        });

        linear38.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intentextra.setClass(getApplicationContext(), ProfileActivity.class);
                intentextra.putExtra("uid", "KGiEfKIV9SZb4dWn9kz5b2mHqiy1");
                startActivity(intentextra);
            }
        });

        linear18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intentextra.setClass(getApplicationContext(), ProfileActivity.class);
                intentextra.putExtra("uid", "C0DTLHB86ocKJsfYSs9HIVMne832");
                startActivity(intentextra);
            }
        });

        linear20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intentextra.setClass(getApplicationContext(), ProfileActivity.class);
                intentextra.putExtra("uid", "LN8dNfhd1wXPcoVexg8vFeLKhks1");
                startActivity(intentextra);
            }
        });

        _profile_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals("kGGvvEghj5XfVBuX1obf2hTrjoc2")) {
                    textview14.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("XwzlFNWx8keGp1QaCHthA2QVbED2")) {
                    textview15.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("1Vy4K7yIVRbhbdnUxcjmRLQ3v2t2")) {
                    textview17.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("C0DTLHB86ocKJsfYSs9HIVMne832")) {
                    textview20.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("LN8dNfhd1wXPcoVexg8vFeLKhks1")) {
                    textview21.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("5FItT6oZk9PX67Z0yRspUVpghIK2")) {
                    textview23.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("nGBcLSDBxgdacBFCud9FNPKEMJD3")) {
                    textview27.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("tO70jxIr23V05U396Pck9SUUAKy2")) {
                    textview29.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("8i03R0bTjoRSEXYNmC60Spt5nse2")) {
                    textview31.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("l9PruxPML6REkTNX9kkptMwsz2k2")) {
                    textview33.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("pi9jaUVRZHWsaMkekOukiH4jTiR2")) {
                    textview35.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("KGiEfKIV9SZb4dWn9kz5b2mHqiy1")) {
                    textview37.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("UpQOFTwn76RAsKcntwPvi6P90KA2")) {
                    textview39.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("Juc0IA2zV8PgIaHnDLFThqf39lr2")) {
                    textview41.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("v2uhfHvCAdSouilgGXHGBD5fdgA3")) {
                    textview43.setText(_childValue.get("username").toString());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals("kGGvvEghj5XfVBuX1obf2hTrjoc2")) {
                    textview14.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("XwzlFNWx8keGp1QaCHthA2QVbED2")) {
                    textview15.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("1Vy4K7yIVRbhbdnUxcjmRLQ3v2t2")) {
                    textview17.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("C0DTLHB86ocKJsfYSs9HIVMne832")) {
                    textview20.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("LN8dNfhd1wXPcoVexg8vFeLKhks1")) {
                    textview21.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("5FItT6oZk9PX67Z0yRspUVpghIK2")) {
                    textview23.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("nGBcLSDBxgdacBFCud9FNPKEMJD3")) {
                    textview27.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("tO70jxIr23V05U396Pck9SUUAKy2")) {
                    textview29.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("8i03R0bTjoRSEXYNmC60Spt5nse2")) {
                    textview31.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("l9PruxPML6REkTNX9kkptMwsz2k2")) {
                    textview33.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("pi9jaUVRZHWsaMkekOukiH4jTiR2")) {
                    textview35.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("KGiEfKIV9SZb4dWn9kz5b2mHqiy1")) {
                    textview37.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("UpQOFTwn76RAsKcntwPvi6P90KA2")) {
                    textview39.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("Juc0IA2zV8PgIaHnDLFThqf39lr2")) {
                    textview41.setText(_childValue.get("username").toString());
                }
                if (_childKey.equals("v2uhfHvCAdSouilgGXHGBD5fdgA3")) {
                    textview43.setText(_childValue.get("username").toString());
                }
            }

            @Override
            public void onChildMoved(DataSnapshot _param1, String _param2) {

            }

            @Override
            public void onChildRemoved(DataSnapshot _param1) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();

            }
        };
        profile.addChildEventListener(_profile_child_listener);

        _prof_img_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals("kGGvvEghj5XfVBuX1obf2hTrjoc2")) {
                    imageview6.clearColorFilter();
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview6) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview6.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("XwzlFNWx8keGp1QaCHthA2QVbED2")) {
                    imageview7.clearColorFilter();
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview7) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview7.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("1Vy4K7yIVRbhbdnUxcjmRLQ3v2t2")) {
                    imageview8.clearColorFilter();
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview8) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview8.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("C0DTLHB86ocKJsfYSs9HIVMne832")) {
                    imageview9.clearColorFilter();
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview9) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview9.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("LN8dNfhd1wXPcoVexg8vFeLKhks1")) {
                    imageview10.clearColorFilter();
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview10) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview10.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("5FItT6oZk9PX67Z0yRspUVpghIK2")) {
                    imageview11.clearColorFilter();
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview11) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview11.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("nGBcLSDBxgdacBFCud9FNPKEMJD3")) {
                    imageview13.clearColorFilter();
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview13) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview13.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("tO70jxIr23V05U396Pck9SUUAKy2")) {
                    imageview14.clearColorFilter();
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview14) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview14.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("8i03R0bTjoRSEXYNmC60Spt5nse2")) {
                    imageview15.clearColorFilter();
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview15) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview15.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("l9PruxPML6REkTNX9kkptMwsz2k2")) {
                    imageview16.clearColorFilter();
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview16) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview16.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("pi9jaUVRZHWsaMkekOukiH4jTiR2")) {
                    imageview17.clearColorFilter();
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview17) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview17.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("KGiEfKIV9SZb4dWn9kz5b2mHqiy1")) {
                    imageview18.clearColorFilter();
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview18) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview18.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("UpQOFTwn76RAsKcntwPvi6P90KA2")) {
                    imageview19.clearColorFilter();
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview19) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview19.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("Juc0IA2zV8PgIaHnDLFThqf39lr2")) {
                    imageview20.clearColorFilter();
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview20) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview20.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("v2uhfHvCAdSouilgGXHGBD5fdgA3")) {
                    imageview21.clearColorFilter();
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview21) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview21.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals("kGGvvEghj5XfVBuX1obf2hTrjoc2")) {
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview6) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview6.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("XwzlFNWx8keGp1QaCHthA2QVbED2")) {
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview7) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview7.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("1Vy4K7yIVRbhbdnUxcjmRLQ3v2t2")) {
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview8) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview8.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("C0DTLHB86ocKJsfYSs9HIVMne832")) {
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview9) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview9.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("LN8dNfhd1wXPcoVexg8vFeLKhks1")) {
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview10) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview10.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("5FItT6oZk9PX67Z0yRspUVpghIK2")) {
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview11) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview11.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("nGBcLSDBxgdacBFCud9FNPKEMJD3")) {
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview13) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview13.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("tO70jxIr23V05U396Pck9SUUAKy2")) {
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview14) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview14.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("8i03R0bTjoRSEXYNmC60Spt5nse2")) {
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview15) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview15.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("l9PruxPML6REkTNX9kkptMwsz2k2")) {
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview16) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview16.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("pi9jaUVRZHWsaMkekOukiH4jTiR2")) {
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview17) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview17.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("KGiEfKIV9SZb4dWn9kz5b2mHqiy1")) {
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview18) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview18.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("UpQOFTwn76RAsKcntwPvi6P90KA2")) {
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview19) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview19.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("Juc0IA2zV8PgIaHnDLFThqf39lr2")) {
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview20) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview20.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (_childKey.equals("v2uhfHvCAdSouilgGXHGBD5fdgA3")) {
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview21) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview21.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
            }

            @Override
            public void onChildMoved(DataSnapshot _param1, String _param2) {

            }

            @Override
            public void onChildRemoved(DataSnapshot _param1) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();

            }
        };
        prof_img.addChildEventListener(_prof_img_child_listener);
    }

    private void initializeLogic() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        linear34.setVisibility(View.GONE);
        try {
            android.content.pm.PackageInfo packageInfo = AboutActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0);

            textview4.setText(packageInfo.versionName);
            textview6.setText("2020.07.02_".concat(packageInfo.versionName.concat("-".concat(Integer.toString(packageInfo.versionCode)))));
        } catch (Exception error) {
        }
        linear16.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View _vlol) {
                intentlol.setClass(getApplicationContext(), SwthomeActivity.class);
                intentlol.putExtra("eg", "2");
                startActivity(intentlol);
                return true;
            }
        });
        linear6.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View _vlol) {
                if (_easterEgg > 9) {
                    intentlol.setClass(getApplicationContext(), SwthomeActivity.class);
                    intentlol.putExtra("eg", "0");
                    startActivity(intentlol);
                }
                return true;
            }
        });
        linear7.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View _vlol) {
                if (_easterEgg2 > 9) {
                    intentlol.setClass(getApplicationContext(), SwthomeActivity.class);
                    intentlol.putExtra("eg", "1");
                    startActivity(intentlol);
                }
                return true;
            }
        });
    }
	/*
Glide.with(getApplicationContext()).load(Uri.parse("lol")).into(imageview1);
*/

    public void drawableclass() {
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {

            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void _circleRipple(final String _color, final View _v) {
        android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_color)});
        android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb, null, null);
        _v.setBackground(ripdrb);
    }

    private void _customNav(final String _color) {
        //Code From StackOverFlow.com And Converted By TeamWorks DEV
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = this.getWindow();
            w.setNavigationBarColor(Color.parseColor(_color));
        }
    }

    private void _BlackStatusBarIcons() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void _shadow(final View _v, final double _n) {
        _v.setElevation((float) _n);
    }

    private void _rippleEffect(final View _view, final String _color) {
        _view.setBackground(Drawables.getSelectableDrawableFor(Color.parseColor(_color)));
        _view.setClickable(true);
    }

    private void _shape(final double _tl, final double _tr, final double _bl, final double _br, final String _BGcolor, final String _Scolor, final double _Swidth, final View _view) {
        Double tlr = _tl;
        Double trr = _tr;
        Double blr = _bl;
        Double brr = _br;
        Double sw = _Swidth;
        android.graphics.drawable.GradientDrawable s = new android.graphics.drawable.GradientDrawable();
        s.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
        s.setCornerRadii(new float[]{tlr.floatValue(), tlr.floatValue(), trr.floatValue(), trr.floatValue(), blr.floatValue(), blr.floatValue(), brr.floatValue(), brr.floatValue()});
        s.setColor(Color.parseColor(_BGcolor));
        s.setStroke(sw.intValue(), Color.parseColor(_Scolor));
        _view.setBackground(s);
    }

    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public int getLocationX(View _v) {
        int[] _location = new int[2];
        _v.getLocationInWindow(_location);
        return _location[0];
    }

    @Deprecated
    public int getLocationY(View _v) {
        int[] _location = new int[2];
        _v.getLocationInWindow(_location);
        return _location[1];
    }

    @Deprecated
    public int getRandom(int _min, int _max) {
        Random random = new Random();
        return random.nextInt(_max - _min + 1) + _min;
    }

    @Deprecated
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
        ArrayList<Double> _result = new ArrayList<Double>();
        SparseBooleanArray _arr = _list.getCheckedItemPositions();
        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
            if (_arr.valueAt(_iIdx))
                _result.add((double) _arr.keyAt(_iIdx));
        }
        return _result;
    }

    @Deprecated
    public float getDip(int _input) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
    }

    @Deprecated
    public int getDisplayWidthPixels() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    public static class Drawables {
        public static android.graphics.drawable.Drawable getSelectableDrawableFor(int color) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                android.graphics.drawable.StateListDrawable stateListDrawable = new android.graphics.drawable.StateListDrawable();
                stateListDrawable.addState(
                        new int[]{android.R.attr.state_pressed},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#ffffff"))
                );
                stateListDrawable.addState(
                        new int[]{android.R.attr.state_focused},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
                );
                stateListDrawable.addState(
                        new int[]{},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
                );
                return stateListDrawable;
            } else {
                android.content.res.ColorStateList pressedColor = android.content.res.ColorStateList.valueOf(color);
                android.graphics.drawable.ColorDrawable defaultColor = new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"));

                android.graphics.drawable.Drawable rippleColor = getRippleColor(color);
                return new android.graphics.drawable.RippleDrawable(
                        pressedColor,
                        defaultColor,
                        rippleColor
                );
            }
        }

        private static android.graphics.drawable.Drawable getRippleColor(int color) {
            float[] outerRadii = new float[8];
            Arrays.fill(outerRadii, 0);
            android.graphics.drawable.shapes.RoundRectShape r = new android.graphics.drawable.shapes.RoundRectShape(outerRadii, null, null);

            android.graphics.drawable.ShapeDrawable shapeDrawable = new
                    android.graphics.drawable.ShapeDrawable(r);
            shapeDrawable.getPaint().setColor(color);
            return shapeDrawable;
        }

        private static int lightenOrDarken(int color, double fraction) {
            if (canLighten(color, fraction)) {
                return lighten(color, fraction);
            } else {
                return darken(color, fraction);
            }
        }

        private static int lighten(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            red = lightenColor(red, fraction);
            green = lightenColor(green, fraction);
            blue = lightenColor(blue, fraction);
            int alpha = Color.alpha(color);
            return Color.argb(alpha, red, green, blue);
        }

        private static int darken(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            red = darkenColor(red, fraction);
            green = darkenColor(green, fraction);
            blue = darkenColor(blue, fraction);
            int alpha = Color.alpha(color);

            return Color.argb(alpha, red, green, blue);
        }

        private static boolean canLighten(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            return canLightenComponent(red, fraction)
                    && canLightenComponent(green, fraction)
                    && canLightenComponent(blue, fraction);
        }

        private static boolean canLightenComponent(int colorComponent, double fraction) {
            int red = Color.red(colorComponent);
            int green = Color.green(colorComponent);
            int blue = Color.blue(colorComponent);
            return red + (red * fraction) < 255
                    && green + (green * fraction) < 255
                    && blue + (blue * fraction) < 255;
        }

        private static int darkenColor(int color, double fraction) {
            return (int) Math.max(color - (color * fraction), 0);
        }

        private static int lightenColor(int color, double fraction) {
            return (int) Math.min(color + (color * fraction), 255);
        }
    }

    public static class CircleDrawables {
        public static android.graphics.drawable.Drawable getSelectableDrawableFor(int color) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                android.graphics.drawable.StateListDrawable stateListDrawable = new android.graphics.drawable.StateListDrawable();
                stateListDrawable.addState(
                        new int[]{android.R.attr.state_pressed},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#ffffff"))
                );
                stateListDrawable.addState(
                        new int[]{android.R.attr.state_focused},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
                );
                stateListDrawable.addState(
                        new int[]{},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
                );
                return stateListDrawable;
            } else {
                android.content.res.ColorStateList pressedColor = android.content.res.ColorStateList.valueOf(color);
                android.graphics.drawable.ColorDrawable defaultColor = new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"));

                android.graphics.drawable.Drawable rippleColor = getRippleColor(color);
                return new android.graphics.drawable.RippleDrawable(
                        pressedColor,
                        defaultColor,
                        rippleColor
                );
            }
        }

        private static android.graphics.drawable.Drawable getRippleColor(int color) {
            float[] outerRadii = new float[180];
            Arrays.fill(outerRadii, 80);
            android.graphics.drawable.shapes.RoundRectShape r = new android.graphics.drawable.shapes.RoundRectShape(outerRadii, null, null);

            android.graphics.drawable.ShapeDrawable shapeDrawable = new
                    android.graphics.drawable.ShapeDrawable(r);
            shapeDrawable.getPaint().setColor(color);
            return shapeDrawable;
        }

        private static int lightenOrDarken(int color, double fraction) {
            if (canLighten(color, fraction)) {
                return lighten(color, fraction);
            } else {
                return darken(color, fraction);
            }
        }

        private static int lighten(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            red = lightenColor(red, fraction);
            green = lightenColor(green, fraction);
            blue = lightenColor(blue, fraction);
            int alpha = Color.alpha(color);
            return Color.argb(alpha, red, green, blue);
        }

        private static int darken(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            red = darkenColor(red, fraction);
            green = darkenColor(green, fraction);
            blue = darkenColor(blue, fraction);
            int alpha = Color.alpha(color);

            return Color.argb(alpha, red, green, blue);
        }

        private static boolean canLighten(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            return canLightenComponent(red, fraction)
                    && canLightenComponent(green, fraction)
                    && canLightenComponent(blue, fraction);
        }

        private static boolean canLightenComponent(int colorComponent, double fraction) {
            int red = Color.red(colorComponent);
            int green = Color.green(colorComponent);
            int blue = Color.blue(colorComponent);
            return red + (red * fraction) < 255
                    && green + (green * fraction) < 255
                    && blue + (blue * fraction) < 255;
        }

        private static int darkenColor(int color, double fraction) {
            return (int) Math.max(color - (color * fraction), 0);
        }

        private static int lightenColor(int color, double fraction) {
            return (int) Math.min(color + (color * fraction), 255);
        }
    }

}
