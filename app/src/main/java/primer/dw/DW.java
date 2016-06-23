package primer.dw;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

public class DW extends AppCompatActivity {


    private View mContentView;

    private ImageView bufferedImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // Note that system bars will only be "visible" if none of the
                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            hide();;
                        }
                    }
                });

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen2);
        mContentView = findViewById(R.id.fullscreen_content);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView scrambleButton = (ImageView) findViewById(R.id.scramble);
        scrambleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.fullscreen_content);
                relativeLayout.removeAllViews();
                scramble();
            }
        });

        scramble();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hide();
    }

    private void scramble(){

        bufferedImage = null;
        Logic.shuffle();

        LinearLayout verticalLayout = new LinearLayout(this);
        verticalLayout.setOrientation(LinearLayout.VERTICAL);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.fullscreen_content);
        relativeLayout.addView(verticalLayout);

        populate(verticalLayout);
    }

    private void hide() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }


    private void emptyView(ViewGroup view){
        view.removeAllViews();
    }

    private void populate(LinearLayout verticalLayout){
        int[][][] cards = Logic.cards;

        for (int[][] row: cards){
            LinearLayout horisontalLayout = new LinearLayout(this);
            horisontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            verticalLayout.addView(horisontalLayout);
            horisontalLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

            for (int[] card: row){
                String s = card[0] + "_" + card[1];
                populateRow(horisontalLayout, s);
            }
        }
    }

    private void populateRow(LinearLayout horisontalLayout, String string){

        final ImageView imageView = new ImageView(this);
        int id = getResources().getIdentifier("primer.dw:drawable/s" + string, null, null);
        imageView.setImageResource(id);

        horisontalLayout.addView(imageView);

        imageView.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        imageView.setPadding(0, 20, 0, 20);

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (bufferedImage == null) {
                    bufferedImage = imageView;
                }

                LinearLayout b_r = (LinearLayout) bufferedImage.getParent();
                int b_x = b_r.indexOfChild(bufferedImage);

                LinearLayout i_r = (LinearLayout) imageView.getParent();
                int i_x = i_r.indexOfChild(imageView);

                LinearLayout vertical = (LinearLayout) b_r.getParent();
                int b_y = vertical.indexOfChild(b_r);
                int i_y = vertical.indexOfChild(i_r);

                int b_a =  Logic.cards[b_y][b_x][0];
                int b_b = Logic.cards[b_y][b_x][1];

                int i_a =  Logic.cards[i_y][i_x][0];
                int i_b = Logic.cards[i_y][i_x][1];

                System.out.println(b_y + " " + b_x + " " + i_y + " " + i_x);


                if(i_b != 0 | ! Logic.isValidSwap(b_y, b_x, i_y, i_x)){
                    int id1 = getResources().getIdentifier("primer.dw:drawable/s" + b_a + "_" + b_b, null, null);
                    bufferedImage.setImageResource(id1);
                    int id2 = getResources().getIdentifier("primer.dw:drawable/s" + i_a + "_" + i_b + "d", null, null);
                    imageView.setImageResource(id2);
                    bufferedImage = imageView;
                }
                else{
                    swapPos(bufferedImage, imageView);
                    int id = getResources().getIdentifier("primer.dw:drawable/s" + b_a + "_" + b_b, null, null);
                    bufferedImage.setImageResource(id);
                    bufferedImage = null;
                }
            }
        });
    }

    private void swapPos(ImageView bufferedImage, ImageView tempImage){

        LinearLayout buffer_row = (LinearLayout) bufferedImage.getParent();
        int buffer_index = buffer_row.indexOfChild(bufferedImage);

        LinearLayout temp_row = (LinearLayout) tempImage.getParent();
        int temp_index = temp_row.indexOfChild(tempImage);

        LinearLayout layout = (LinearLayout) buffer_row.getParent();
        int br = layout.indexOfChild(buffer_row);
        int tr = layout.indexOfChild(temp_row);

        buffer_row.removeView(bufferedImage);
        temp_row.removeView(tempImage);

        if (buffer_index < temp_index){
            try{
                buffer_row.addView(tempImage, buffer_index);
            }
            catch (IndexOutOfBoundsException e){
                buffer_row.addView(tempImage);
            }
            try {
                temp_row.addView(bufferedImage, temp_index);
            }
            catch (IndexOutOfBoundsException e){
                temp_row.addView(bufferedImage);
            }
        }
        else {
            try {
                temp_row.addView(bufferedImage, temp_index);
            }
            catch (IndexOutOfBoundsException e){
                temp_row.addView(bufferedImage);
            }
            try{
                buffer_row.addView(tempImage, buffer_index);
            }
            catch (IndexOutOfBoundsException e){
                buffer_row.addView(tempImage);
            }
        }
        Logic.swap(tr, temp_index, br, buffer_index);

    }
}
