package rocketpotatoes.authout.Helpers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static String bitmapToBase64(Bitmap bitmap, int quality) {
        if (quality > 100 || quality < 0) {
            throw new IllegalArgumentException("Quality must be between 0 and 100");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    /**
     * @param view         View to animate
     * @param toVisibility Visibility at the end of animation
     * @param toAlpha      Alpha at the end of animation
     * @param duration     Animation duration in ms
     */
    public static void animateView(final View view, final int toVisibility, float toAlpha, int duration) {
        boolean show = toVisibility == View.VISIBLE;
        if (show) {
            view.setAlpha(0);
        }
        view.setVisibility(View.VISIBLE);
        view.animate()
                .setDuration(duration)
                .alpha(show ? toAlpha : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(toVisibility);
                    }
                });
    }


    /** Builds are returns a list of the generic or trusted children of a parent
     *
     * @param response - the response from the server with the parent details
     * @param getTrustedChildren - Where to get the trusted children or get generic children
     * @return list of {@link Child} objects specified in {@param getTrustedChildren}
     */
    public static List<Child> buildChildList(JSONObject response, boolean getTrustedChildren) {
        String key = getTrustedChildren ? "trusted_children" : "children";
        List<Child> childList = new ArrayList<>();
        try {
            String childrenString = response.get(key).toString();
            childrenString = childrenString.substring(1, childrenString.length() - 1);
            String[] children = childrenString.split(",\\{");
            for (int i = 0; i < children.length; i++) {
                String child = children[i];
                if (i > 0) {
                    child = "{" + child; //todo this is dodge as fuck clean it up
                }

                JSONObject childObject = new JSONObject(child);
                childList.add(new Child(childObject.get("first_name").toString(),
                        childObject.get("last_name").toString(),
                        childObject.get("status").toString().equals("false") ? "Signed-Out" : "Signed-In",
                        Integer.parseInt(childObject.get("id").toString())));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return childList;
    }

    /** Builds and returns a parent object
     *
     * @param response - the response from the server with the parent details
     * @param children - a list of child objects
     * @param trustedChildren - a list of child objects
     * @return
     */
    public static Parent buildParent(JSONObject response, List<Child> children, List<Child> trustedChildren) {
        try {
            String firstName = response.get("first_name").toString();
            String lastName = response.get("last_name").toString();
            int id = Integer.parseInt(response.get("id").toString());
            return new Parent(firstName, lastName, children, trustedChildren, id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Issue instantiating parent");
    }
}
