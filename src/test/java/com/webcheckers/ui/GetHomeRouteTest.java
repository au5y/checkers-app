package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class GetHomeRouteTest
{
    /**
     * The unit test for the {@link GetHomeRoute}
     *
     * @author Zehra Amena Baig 'zab1166'
     */

    /*
     * The component under test (CuT)
     */
    private GetHomeRoute CuT;

    /*
     * Friendly objects
     */
    private PlayerLobby lobby;
    private Player p1, p2;

    /*
       Mock objects
     */
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    @BeforeEach
    public void setup()
    {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        lobby = new PlayerLobby();
        p1 = new Player(lobby);
        p1.setUsername("Username1");
        p2 = new Player(lobby);
        p2.setUsername("Username2");
        lobby.newPlayer(p1);
        lobby.newPlayer(p2);
        //Create a unique CuT for each test.
        CuT = new GetHomeRoute(engine, lobby);
    }

    /**
    Test that CuT shows the Home view when the session is brand new.
    */
    @Test
    public void new_session() {
        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        final TemplateEngineTest testHelper = new TemplateEngineTest();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        CuT.handle(request, response);

        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(GetHomeRoute.NEW_PLAYER_ATTR, Boolean.TRUE);
        testHelper.assertViewModelAttribute(GetHomeRoute.CHALLENGED_KEY, null);
        testHelper.assertViewModelAttribute(GetHomeRoute.SIGN_IN_KEY, false);
        //   * test view name
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
        //   * verify that a player service object and the session timeout watchdog are stored
        //   * in the session.

        verify(session).attribute(eq(GetHomeRoute.PLAYER_LOBBY_KEY), any(PlayerLobby.class));
    }
    

    @Test
    public void oldSession()
    {
        session.attribute(GetHomeRoute.PLAYER_LOBBY_KEY, lobby);
        session.attribute(GetHomeRoute.PLAYER_KEY, p1);
        final TemplateEngineTest testHelper = new TemplateEngineTest();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute(GetHomeRoute.PLAYER_LOBBY_KEY)).thenReturn(lobby);
        when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(p1);

        // Invoke the test
        CuT.handle(request, response);
        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();


        lobby.challenge(p1.getUsername(), p2.getUsername());
        lobby.startGame(p2.getUsername(), p1.getUsername());
        assertTrue(p1.isInGame());



    }

}
