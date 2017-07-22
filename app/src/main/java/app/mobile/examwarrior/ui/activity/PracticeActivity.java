package app.mobile.examwarrior.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.adapters.questions.BannerPagerAdapter;
import app.mobile.examwarrior.listener.NextClicklistener;
import app.mobile.examwarrior.widget.widgets.SlidingTabLayout;

public class PracticeActivity extends AppCompatActivity implements NextClicklistener{

 /*String qw="$${1+√5}/2=1+1/{1+1/{1+⋯}}$$"+"$$[\\O\\H^{-}]=K_\\W/[\\H^{+}]$$"+"\\[\\table x_0^2, {_0F_1(; a; z)}, R_i_^j_k_l\\]"+"\\(U=⋃↙αU_α⇒0→\\fr F(U)→∏↙α\\fr F(U_α)→↖{-}∏↙{α,β}\\fr F(U_α∩U_β)\\) is exact"
            +"\\[∫_\\Δd\\bo ω=∫_{∂\\Δ}\\bo ω\\]"+"\\[∀x_0∀ε>0∃δ>0∋{|x-x_0|}&lt;δ⇒{|f(x)-f(x_0)|}&lt;ε\\]"+"$$∑↙{i=0}↖n i={n(n+1)}/2$$";
    String str_0="$$f'(x)=\\lim_{h\\to 0}\\frac{f(x+h)-f(x)}{h}$$";
    String str_1 = "<br> inline code: \\(ax^2 + bx + c = 0\\) " +
            "<br> Block formula: $$\\sum_{i=0}^n i^2 = \\frac{(n^2+n)(2n+1)}{6}$$<br>";
    String str_2 = "$$\\sum_{i=0}^n i={n(n+1)}/2$$<br>";
    String str_3 = "$$f'(x)=\\lim_{h \\to0}\\frac{f(x+h)-f(x)}{h}$$";
    String str_4 = "$$f'(x)=\\lim_{h \\to 0}{\\frac f(x+h)-f(x)}/{h}$$";
    String str_5 = "<br><br> inline formula \\(ax^2+bx+c=0\\)";
    String str_6 = "$$ \\int_\\nabla  \\omega=\\int_{\\nabla} \\omega $$";
    String a="$$\\sqrt[n]a \\sqrt[n]b=\\sqrt[n]ab$$";
    String b="$$ \\vec v + \\vec w  = \\vec  v \\vec w + \\cos \\emptyset $$";*/

    /*String abc="\n" +
            "\\[{\\tan ^{ - 1}}\\left( {\\frac{{5x + 8}}{{4{x^2} + 5x + 7}}} \\right)\\]";
    String xyz="\\[a{x^2} + bx + c = 0\\]";
    String a="\\mathop{\\LARGE\\mathrm K}_{i=1}^\\infty \\frac{a_i}{b_i}";
    String pqr="$$f\\left( n \\right) = \\mathop {{\\rm{lim}}}\\limits_{x \\to 0} {\\left( {\\left( {1 + \\sin \\frac{x}{2}} \\right)\\left( {1 + \\sin \\frac{x}{{{x^2}}}} \\right) \\ldots \\left( {1 + \\sin \\frac{x}{{{2^n}}}} \\right)} \\right)^{\\frac{1}{x}}}$$";
    String cvb="$ {{\\rm{lim}}}{\\theta  \\to {0^ + }} \\frac{{{\\rm{area}}\\left( {\\Delta {\\rm{CPO}}} \\right)}}{{{\\rm{si}}{{\\rm{n}}^2}\\theta }}$";
   */
    private ViewPager viewPager;
    private SlidingTabLayout homeTabs;
    private BannerPagerAdapter bannerPagerAdapter;
    ArrayList<String> arraylist = new ArrayList<>();
    String a1="$$\\frac{1}{\\Bigl(\\sqrt{\\phi \\sqrt{5}}-\\phi\\Bigr) e^{\\frac25 \\pi}} = 1+\\frac{e^{-2\\pi}} {1+\\frac{e^{-4\\pi}} {1+\\frac{e^{-6\\pi}} {1+\\frac{e^{-8\\pi}} {1+\\cdots} } } }$$";
    String a2="$$\\left( \\sum_{k=1}^n a_k b_k \\right)^2 \\leq \\left( \\sum_{k=1}^n a_k^2 \\right) \\left( \\sum_{k=1}^n b_k^2 \\right)$$";
    String a3="$$\\displaystyle\\sum_{i=1}^{k+1}i$$";
    String a4="$$\\displaystyle= \\left(\\sum_{i=1}^{k}i\\right) +(k+1)$$";
    String a5="$$\\displaystyle= \\frac{k(k+1)}{2}+k+1$$";
    String a6="$$\\displaystyle= \\frac{k(k+1)+2(k+1)}{2}$$";
    String a7="$$\\displaystyle= \\frac{(k+1)(k+2)}{2}$$";
    String a8="$$\\displaystyle= \\frac{(k+1)((k+1)+1)}{2}$$";
    String a9="$$ \\displaystyle\\text{ for }\\lvert q\\rvert <  == \\displaystyle \\prod_{j=0}^{\\infty}\\frac{1}{(1-q^{5j+2})(1-q^{5j+3})}, \\displaystyle\n" +
            "1 +  \\frac{q^2}{(1-q)}+\\frac{q^6}{(1-q)(1-q^2)}+\\cdots$$";
    String a10="$$k_{n+1} = n^2 + k_n^2 - k_{n-1}$$";
    String a11="$$\\int u \\frac{dv}{dx}\\,dx=uv-\\int \\frac{du}{dx}v\\,dx $$";
    String a12="$$f(x) = \\int_{-\\infty}^\\infty \\hat f(\\xi)\\,e^{2 \\pi i \\xi x}$$";
    String a13="$$\\oint \\vec{F} \\cdot d\\vec{s}=0$$";
    String a14="$$\\mathbf{V}_1 \\times \\mathbf{V}_2 =  \\begin{vmatrix}\n" +
            "\\mathbf{i} & \\mathbf{j} & \\mathbf{k} \\\\\n" +
            "\\frac{\\partial X}{\\partial u} &  \\frac{\\partial Y}{\\partial u} & 0 \\\\\n" +
            "\\frac{\\partial X}{\\partial v} &  \\frac{\\partial Y}{\\partial v} & 0\n" +
            "\\end{vmatrix}$$";
    String a15="$$$$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        homeTabs = (SlidingTabLayout) findViewById(R.id.home_tab_layout);
        arraylist.add(a1);
        arraylist.add(a2);
        arraylist.add(a3);
        arraylist.add(a4);
        arraylist.add(a5);
        arraylist.add(a6);
        arraylist.add(a7);
        arraylist.add(a8);
        arraylist.add(a9);
        arraylist.add(a10);
        arraylist.add(a11);
        arraylist.add(a12);
        arraylist.add(a13);
        arraylist.add(a14);
        arraylist.add(a5);
        arraylist.add(a6);
        arraylist.add(a7);
        arraylist.add(a8);
        arraylist.add(a9);
        arraylist.add(a10);
        arraylist.add(a11);
        arraylist.add(a12);
        arraylist.add(a13);
        arraylist.add(a14);




      /*  arraylist.add(str_1);
        arraylist.add(str_2);
        arraylist.add(a);
        arraylist.add(b);
        arraylist.add(str_3);
        arraylist.add(str_4);
        arraylist.add(str_5);
        arraylist.add(str_6);*/
        viewPager = (ViewPager) findViewById(R.id.pager);
        bannerPagerAdapter = new BannerPagerAdapter(this, arraylist, this);
        viewPager.setAdapter(bannerPagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        homeTabs.setViewPager(viewPager);
    }

    @Override
    public void onNextClick(int position) {
        if (arraylist.size() > position + 1)
            viewPager.setCurrentItem(position + 1);

    }

    @Override
    public void onBackClick(int position) {
        if (position >=0)
            viewPager.setCurrentItem(position - 1);
    }
}
