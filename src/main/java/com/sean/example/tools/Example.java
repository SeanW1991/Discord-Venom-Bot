package com.sean.example.tools;

import com.sean.example.ApplicationContext;
import com.sean.example.database.DatabaseManager;
import com.sean.example.database.impl.GlyphRepository;
import com.sean.example.model.Glyph;
import com.sean.example.model.PlatformType;
import com.sean.example.parser.impl.ApplicationContextParser;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by sean on 10/04/2017.
 */
public final class Example {

    /**
     * Starts the application.
     * @param args The arguments of the application.
     */
    public static void main(String[] args) {
        try {
            ApplicationContext applicationContext = new ApplicationContextParser().parse();
            new Example(applicationContext).start();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private final DatabaseManager databaseManager;

    private final GlyphRepository glyphRepository;

    public Example(ApplicationContext applicationContext) throws SQLException {
        this.databaseManager = new DatabaseManager(applicationContext);
        this.glyphRepository = new GlyphRepository(databaseManager);
    }

    public void start() throws SQLException {

        String guildId = "95577356114075648";

        String accountId = "211185924544004096";

        Flowable<List<Glyph>> glyphList = glyphRepository.findAvailableGlyphsAsFlowable(PlatformType.PC, guildId);
        glyphList.subscribe(glyphs -> glyphs.forEach(glyph -> {
            System.out.println(glyph);
        }), throwable -> System.out.println(throwable.getMessage()));
    }

}
