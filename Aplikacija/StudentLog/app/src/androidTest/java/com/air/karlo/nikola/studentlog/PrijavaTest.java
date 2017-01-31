package com.air.karlo.nikola.studentlog;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Nikola on 31.1.2017..
 */

public class PrijavaTest extends ActivityInstrumentationTestCase2<Prijava> {
    public PrijavaTest() {
        super(Prijava.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void testedittext(){
        EditText et1 = (EditText)getActivity().findViewById(R.id.txtKorIme);
        EditText et2 = (EditText)getActivity().findViewById(R.id.txtLozinka);
        assertNotNull(et1);
        assertNotNull(et2);
    }

    @SmallTest
    public void testbutton(){
        Button btn1 = (Button) getActivity().findViewById(R.id.btnPrijava);

        assertNotNull(btn1);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
