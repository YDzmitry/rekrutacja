package com.lastminute.recruitment.persistence;

import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.WikiPageRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryWikiPageRepository implements WikiPageRepository {

    private final AtomicLong idCounter = new AtomicLong();
    private final Map<Long, WikiPage> db = new HashMap<>();

    @Override
    public void save(WikiPage wikiPage) {
        db.put(idCounter.incrementAndGet(), wikiPage);
        System.out.println("WikiPages in DB:");
        db.keySet().forEach(wikiPageKey ->
                System.out.println(wikiPageKey + "->" + db.get(wikiPageKey))
        );
    }

}
