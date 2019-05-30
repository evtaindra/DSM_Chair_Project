package turingmediastudios.android.Models;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static final String SHARED_PREF_NAME = "storytime_shared_preferences";

    private static SharedPreferencesManager mInstance;
    private Context context;

    public SharedPreferencesManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPreferencesManager getInstance(Context context){
        if (mInstance == null) {
            mInstance = new SharedPreferencesManager(context);

        }

        return mInstance;
    }

    /**
     * ESTE METODO GUARDA LOS DATOS DEL USUARIO LOGUEADO
     * EN SHARED PREFERENCES PARA QUE PERSISTAN LOS DATOS
     * */
    public void saveUser(User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("user_id", user.getUser_id());
        editor.putString("user_name", user.getUser_name());
        editor.putString("user_lastname", user.getUser_lastname());
        editor.putString("user_email", user.getUser_email());
        editor.putString("user_phone", user.getUser_phone());
        editor.putString("user_password", user.getUser_password());

        editor.apply();

    }

    /**
     * CUANDO SE INICIA SESION EL VALOR DE ESTE BOOLEAN
     * CAMBIA AL IGUAL QUE CUANDO SE CIERRA LA SESION
     */
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1) != -1;
    }

    /**
     * SE GUARDA EL USUARIO QUE SE LOGUEA
     */
    public User getLoggedUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        User user = new User(sharedPreferences.getInt("user_id", -1),
                sharedPreferences.getString("user_name", null),
                sharedPreferences.getString("user_lastname", null),
                sharedPreferences.getString("user_email", null),
                sharedPreferences.getString("user_phone", null),
                sharedPreferences.getString("user_password", null));

        return user;
    }

    /**
     * LLAMAMOS ESTA FUNCION CUANDO EL USUARIO CIERRE SESION
     */
    public void clear() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
    }

}
