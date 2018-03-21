package YouTubeAnalizer;

import YouTubeAnalizer.Cache.CacheService;
import YouTubeAnalizer.Entity.Channel;
import YouTubeAnalizer.Settings.SettingsService;
import YouTubeAnalizer.view.SearchForm;
import com.gluonhq.particle.application.Particle;
import com.gluonhq.particle.application.ParticleApplication;
import com.gluonhq.particle.form.Form;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;

import java.lang.reflect.Field;

import static org.controlsfx.control.action.ActionMap.actions;

/**
 * https://developers.google.com/youtube/v3/code_samples/
 * https://developers.google.com/youtube/v3/code_samples/java#search_by_location
 * https://developers.google.com/youtube/v3/guides/auth/server-side-web-apps
 * https://developers.google.com/youtube/v3/docs/channels
 * todo перенести в сервис
 * todo указывать в part минимум (statistics)
 */
public class App extends ParticleApplication
{
    private static SettingsService settingsService = SettingsService.getInstance();
    
    private static final String APPLICATION_NAME = "YouTube Analyzer";
    
  
    
   
    
    
    
    
    public App()
    {
        super( APPLICATION_NAME );
        
        disableNotificationPane();

        settingsService.initSettings();

        CacheService.initStorage();
    }

    @Override
    public void postInit(Scene scene)
    {
        getApp().buildMenu( "File -> [settings,---, exit]", "Help -> [about]" );

        getApp().getToolBarActions().addAll( actions( "settings" ) );
    
    
//        Form<String> form = new Form<String>() {}
//        getApp().getFormManager().registerForm(  );
    
        // http://docs.gluonhq.com/particle/1.1.3/#_singleton_injections
//        getApp().getFormManager()
//                .getForm( SearchForm.class, Form.UpdateMode.UPDATE_NEW_INSTANCE )
//                .ifPresent( form -> form.configure( new Channel( "Mark" ) )
//                                        .resizable( false )
//                                        .showAndWait() );
        
        setOnCloseAction();
    }

    /**
     * Hack to disable pesky advertisement
     */
    private void disableNotificationPane()
    {
        Particle particle = getParticle();

        Field notificationPaneField = null;
        try
        {
            notificationPaneField = particle.getClass().getDeclaredField( "notificationPane" );
        }
        catch ( NoSuchFieldException e )
        {
            e.printStackTrace();
        }

        notificationPaneField.setAccessible( true );

        try
        {
            notificationPaneField.set( particle,  new NotificationPane() );
        }
        catch ( IllegalAccessException e )
        {
            e.printStackTrace();
        }
    }

    private Particle getApp()
    {
        return getParticle();
    }

    // не работает
    void setOnCloseAction()
    {
        Stage stage = getPrimaryStage();

        stage.setOnCloseRequest( event -> {
            System.out.println("Close");
            //CacheService.saveStorage();
        } );
    }
}
