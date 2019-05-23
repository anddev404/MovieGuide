package com.anddev.movieguide;

import com.anddev.movieguide.model.Actor;
import com.google.gson.Gson;

import java.util.List;

public abstract class KnownFor {


    private int id;
    private List<String> crew;
    private List<Cast> cast;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getCrew() {
        return crew;
    }

    public void setCrew(List<String> crew) {
        this.crew = crew;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public static KnownFor getExampleKnownFor() {
        try {
            String jsonString = "{\n" +
                    "  \"cast\": [\n" +
                    "    {\n" +
                    "      \"character\": \"Bacon\",\n" +
                    "      \"credit_id\": \"52fe4217c3a36847f8003607\",\n" +
                    "      \"poster_path\": \"/qV7QaSf7f7yC2lc985zfyOJIAIN.jpg\",\n" +
                    "      \"id\": 100,\n" +
                    "      \"video\": false,\n" +
                    "      \"vote_count\": 3019,\n" +
                    "      \"adult\": false,\n" +
                    "      \"backdrop_path\": \"/kzeR7BA0htJ7BeI6QEUX3PVp39s.jpg\",\n" +
                    "      \"genre_ids\": [\n" +
                    "        35,\n" +
                    "        80\n" +
                    "      ],\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"Lock, Stock and Two Smoking Barrels\",\n" +
                    "      \"popularity\": 7.351,\n" +
                    "      \"title\": \"Lock, Stock and Two Smoking Barrels\",\n" +
                    "      \"vote_average\": 8,\n" +
                    "      \"overview\": \"A card shark and his unwillingly-enlisted friends need to make a lot of cash quick after losing a sketchy poker match. To do this they decide to pull a heist on a small-time gang who happen to be operating out of the flat next door.\",\n" +
                    "      \"release_date\": \"1999-03-05\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"character\": \"Turkish\",\n" +
                    "      \"credit_id\": \"52fe4218c3a36847f8003be1\",\n" +
                    "      \"poster_path\": \"/on9JlbGEccLsYkjeEph2Whm1DIp.jpg\",\n" +
                    "      \"id\": 107,\n" +
                    "      \"video\": false,\n" +
                    "      \"vote_count\": 4703,\n" +
                    "      \"adult\": false,\n" +
                    "      \"backdrop_path\": \"/lE1srrhvN9LHmJrFIrBkdo6NqSh.jpg\",\n" +
                    "      \"genre_ids\": [\n" +
                    "        53,\n" +
                    "        80\n" +
                    "      ],\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"Snatch\",\n" +
                    "      \"popularity\": 15.297,\n" +
                    "      \"title\": \"Snatch\",\n" +
                    "      \"vote_average\": 7.8,\n" +
                    "      \"overview\": \"The second film from British director Guy Ritchie. Snatch tells an obscure story similar to his first fast-paced crazy character-colliding filled film “Lock, Stock and Two Smoking Barrels.” There are two overlapping stories here – one is the search for a stolen diamond, and the other about a boxing promoter who’s having trouble with a psychotic gangster.\",\n" +
                    "      \"release_date\": \"2001-01-19\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"character\": \"Sgt. Jericho Butler\",\n" +
                    "      \"credit_id\": \"52fe43089251416c75000d79\",\n" +
                    "      \"poster_path\": \"/rBmkaKxRg55zBZr11EGbedFJM0.jpg\",\n" +
                    "      \"id\": 10016,\n" +
                    "      \"video\": false,\n" +
                    "      \"vote_count\": 490,\n" +
                    "      \"adult\": false,\n" +
                    "      \"backdrop_path\": \"/jF81VZCGjc6YJS2KqiPVqoYVuMq.jpg\",\n" +
                    "      \"genre_ids\": [\n" +
                    "        28,\n" +
                    "        27,\n" +
                    "        878\n" +
                    "      ],\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"Ghosts of Mars\",\n" +
                    "      \"popularity\": 9.275,\n" +
                    "      \"title\": \"Ghosts of Mars\",\n" +
                    "      \"vote_average\": 5,\n" +
                    "      \"overview\": \"Melanie Ballard is a hard nosed police chief in the year 2025. She and a police snatch squad are sent to Mars to apprehend dangerous criminal James Williams. Mars has been occupied by humans for some time and they have set up mining facilities. The mining activities on Mars have unleashed the spirits of alien beings who gradually possess the bodies of the workers. It soon turns out that catching the dangerous fugitive takes a back seat as the alien spirits begin to rid their planet of the 'invaders'.\",\n" +
                    "      \"release_date\": \"2001-08-24\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"character\": \"Chev Chelios\",\n" +
                    "      \"credit_id\": \"52fe4325c3a36847f803dc2b\",\n" +
                    "      \"poster_path\": \"/6RnD3Zfh0tO0cD2vroWyH0F98Ej.jpg\",\n" +
                    "      \"id\": 1948,\n" +
                    "      \"video\": false,\n" +
                    "      \"vote_count\": 1891,\n" +
                    "      \"adult\": false,\n" +
                    "      \"backdrop_path\": \"/kSTvteM3ekuoeO7u7GJtZQkZuom.jpg\",\n" +
                    "      \"genre_ids\": [\n" +
                    "        28,\n" +
                    "        53,\n" +
                    "        80\n" +
                    "      ],\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"Crank\",\n" +
                    "      \"popularity\": 18.158,\n" +
                    "      \"title\": \"Crank\",\n" +
                    "      \"vote_average\": 6.6,\n" +
                    "      \"overview\": \"Professional assassin Chev Chelios learns his rival has injected him with a poison that will kill him if his heart rate drops.\",\n" +
                    "      \"release_date\": \"2006-08-31\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"character\": \"Farmer\",\n" +
                    "      \"credit_id\": \"52fe434dc3a36847f8049e1d\",\n" +
                    "      \"poster_path\": \"/bbN1lmDk1PT0GsTFCy179sk5nIF.jpg\",\n" +
                    "      \"id\": 2312,\n" +
                    "      \"video\": false,\n" +
                    "      \"vote_count\": 372,\n" +
                    "      \"adult\": false,\n" +
                    "      \"backdrop_path\": \"/u14b6vdFieOm9OXGa49c7n5gmQS.jpg\",\n" +
                    "      \"genre_ids\": [\n" +
                    "        12,\n" +
                    "        14,\n" +
                    "        28,\n" +
                    "        18\n" +
                    "      ],\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"In the Name of the King: A Dungeon Siege Tale\",\n" +
                    "      \"popularity\": 7.567,\n" +
                    "      \"title\": \"In the Name of the King: A Dungeon Siege Tale\",\n" +
                    "      \"vote_average\": 4.4,\n" +
                    "      \"overview\": \"A man named Farmer sets out to rescue his kidnapped wife and avenge the death of his son -- two acts committed by the Krugs, a race of animal-warriors who are controlled by the evil Gallian.\",\n" +
                    "      \"release_date\": \"2008-01-11\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"character\": \"Special Agent Jack Crawford\",\n" +
                    "      \"credit_id\": \"52fe436f9251416c7501035b\",\n" +
                    "      \"poster_path\": \"/envBC1uSYRWVj2p3Im8XbPKZ4B5.jpg\",\n" +
                    "      \"id\": 10431,\n" +
                    "      \"video\": false,\n" +
                    "      \"vote_count\": 698,\n" +
                    "      \"adult\": false,\n" +
                    "      \"backdrop_path\": \"/4ivjMedEegDXejLGobEzMvswPBe.jpg\",\n" +
                    "      \"genre_ids\": [\n" +
                    "        28\n" +
                    "      ],\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"War\",\n" +
                    "      \"popularity\": 13.684,\n" +
                    "      \"title\": \"War\",\n" +
                    "      \"vote_average\": 6.1,\n" +
                    "      \"overview\": \"FBI agent Jack Crawford is out for revenge when his partner is killed and all clues point to the mysterious assassin Rogue. But when Rogue turns up years later to take care of some unfinished business, he triggers a violent clash of rival gangs. Will the truth come out before it's too late? And when the dust settles, who will remain standing?\",\n" +
                    "      \"release_date\": \"2007-08-24\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"character\": \"Jensen Ames\",\n" +
                    "      \"credit_id\": \"52fe43789251416c75011ac3\",\n" +
                    "      \"poster_path\": \"/3dIZ049AxmvrvNiXKPc36YNum7g.jpg\",\n" +
                    "      \"id\": 10483,\n" +
                    "      \"video\": false,\n" +
                    "      \"vote_count\": 2009,\n" +
                    "      \"adult\": false,\n" +
                    "      \"backdrop_path\": \"/m0JIsX9SLfHLxU1q6ud3ifJjXmC.jpg\",\n" +
                    "      \"genre_ids\": [\n" +
                    "        28,\n" +
                    "        53,\n" +
                    "        878\n" +
                    "      ],\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"Death Race\",\n" +
                    "      \"popularity\": 12.631,\n" +
                    "      \"title\": \"Death Race\",\n" +
                    "      \"vote_average\": 6.1,\n" +
                    "      \"overview\": \"Terminal Island, New York: 2020. Overcrowding in the US penal system has reached a breaking point. Prisons have been turned over to a monolithic Weyland Corporation, which sees jails full of thugs as an opportunity for televised sport. Adrenalized inmates, a global audience hungry for violence and a spectacular, enclosed arena come together to form the 'Death Race', the biggest, most brutal event.\",\n" +
                    "      \"release_date\": \"2008-08-22\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"character\": \"Frank Martin\",\n" +
                    "      \"credit_id\": \"52fe43adc3a36847f806714f\",\n" +
                    "      \"poster_path\": \"/vD5plFV1ec9CSIsdlPe9icCDRTL.jpg\",\n" +
                    "      \"id\": 4108,\n" +
                    "      \"video\": false,\n" +
                    "      \"vote_count\": 2814,\n" +
                    "      \"adult\": false,\n" +
                    "      \"backdrop_path\": \"/fhbMK7Mf69qCE7DR7g2gJxfgUjU.jpg\",\n" +
                    "      \"genre_ids\": [\n" +
                    "        28,\n" +
                    "        80,\n" +
                    "        53\n" +
                    "      ],\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"The Transporter\",\n" +
                    "      \"popularity\": 13.572,\n" +
                    "      \"title\": \"The Transporter\",\n" +
                    "      \"vote_average\": 6.6,\n" +
                    "      \"overview\": \"Former Special Forces officer, Frank Martin will deliver anything to anyone for the right price, and his no-questions-asked policy puts him in high demand. But when he realizes his latest cargo is alive, it sets in motion a dangerous chain of events. The bound and gagged Lai is being smuggled to France by a shady American businessman, and Frank works to save her as his own illegal activities are uncovered by a French detective.\",\n" +
                    "      \"release_date\": \"2002-10-02\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"character\": \"MVA Agent Evan Funsch\",\n" +
                    "      \"credit_id\": \"52fe43b89251416c7501b909\",\n" +
                    "      \"poster_path\": \"/baw9Zdb1zJDWs4iJBrmfWKnx62p.jpg\",\n" +
                    "      \"id\": 10796,\n" +
                    "      \"video\": false,\n" +
                    "      \"vote_count\": 714,\n" +
                    "      \"adult\": false,\n" +
                    "      \"backdrop_path\": \"/nCK3Api5TteYOhbc7JTrbcD9OlO.jpg\",\n" +
                    "      \"genre_ids\": [\n" +
                    "        28,\n" +
                    "        878,\n" +
                    "        53\n" +
                    "      ],\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"The One\",\n" +
                    "      \"popularity\": 13.714,\n" +
                    "      \"title\": \"The One\",\n" +
                    "      \"vote_average\": 5.9,\n" +
                    "      \"overview\": \"A sheriff's deputy fights an alternate universe version of himself who grows stronger with each alternate self he kills.\",\n" +
                    "      \"release_date\": \"2001-11-02\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"character\": \"Jake\",\n" +
                    "      \"credit_id\": \"52fe43c39251416c7501d26d\",\n" +
                    "      \"release_date\": \"2005-09-22\",\n" +
                    "      \"vote_count\": 632,\n" +
                    "      \"video\": false,\n" +
                    "      \"adult\": false,\n" +
                    "      \"vote_average\": 6.4,\n" +
                    "      \"title\": \"Revolver\",\n" +
                    "      \"genre_ids\": [\n" +
                    "        80,\n" +
                    "        18,\n" +
                    "        9648,\n" +
                    "        53\n" +
                    "      ],\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"Revolver\",\n" +
                    "      \"popularity\": 7.8,\n" +
                    "      \"id\": 10851,\n" +
                    "      \"backdrop_path\": \"/61Kpt3kubCNNY3xFdv20Jon3Axc.jpg\",\n" +
                    "      \"overview\": \"Hotshot gambler Jake Green (Jason Statham) is long on bravado and seriously short of common sense. Rarely is he allowed in any casino because he's a bona fide winner and, in fact, has taken so much money over the years that he's the sole client of his accountant elder brother, Billy. Invited to a private game, Jake is in fear of losing his life.\",\n" +
                    "      \"poster_path\": \"/jscEs0gtxoAwKcF4509oYAFDXIw.jpg\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"character\": \"Det. Quentin Conners\",\n" +
                    "      \"credit_id\": \"52fe4402c3a36847f807d077\",\n" +
                    "      \"poster_path\": \"/pgH45ufjjmfSrHvuyOnNaiWl8oM.jpg\",\n" +
                    "      \"id\": 5289,\n" +
                    "      \"video\": false,\n" +
                    "      \"vote_count\": 448,\n" +
                    "      \"adult\": false,\n" +
                    "      \"backdrop_path\": \"/ni3DtIc0rm7OgxenOPKEktUjgsU.jpg\",\n" +
                    "      \"genre_ids\": [\n" +
                    "        18,\n" +
                    "        28,\n" +
                    "        80\n" +
                    "      ],\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"Chaos\",\n" +
                    "      \"popularity\": 9.537,\n" +
                    "      \"title\": \"Chaos\",\n" +
                    "      \"vote_average\": 6.2,\n" +
                    "      \"overview\": \"In Seattle, detective Quentin Conners is unfairly suspended and his partner Jason York leaves the police force after a tragic shooting on Pearl Street Bridge, when the hostage and the criminal die. During a bank heist with a hostage situation, Conners is assigned in charge of the operation with the rookie Shane Dekker as his partner. The thieves, lead by Lorenz, apparently do not steal a penny from the bank. While chasing the gangsters, the police team disclose that they planted a virus in the system, stealing one billion dollars from the different accounts, using the principle of the Chaos Theory. Further, they find that Lorenz is killing his accomplices.\",\n" +
                    "      \"release_date\": \"2005-01-17\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"character\": \"Bateman\",\n" +
                    "      \"credit_id\": \"52fe4481c3a36847f809a32b\",\n" +
                    "      \"poster_path\": \"/pnTRNDfdxdyx2tYKrTPlO3rcKk4.jpg\",\n" +
                    "      \"id\": 7515,\n" +
                    "      \"video\": false,\n" +
                    "      \"vote_count\": 147,\n" +
                    "      \"adult\": false,\n" +
                    "      \"backdrop_path\": \"/qOu2ZH1beY391NPq9Xev09hg04Z.jpg\",\n" +
                    "      \"genre_ids\": [\n" +
                    "        18,\n" +
                    "        28,\n" +
                    "        10749\n" +
                    "      ],\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"London\",\n" +
                    "      \"popularity\": 8.863,\n" +
                    "      \"title\": \"London\",\n" +
                    "      \"vote_average\": 6,\n" +
                    "      \"overview\": \"London is a drug laden adventure that centers on a party in a New York loft where a young man is trying to win back his ex-girlfriend.\",\n" +
                    "      \"release_date\": \"2005-02-10\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"character\": \"Terry Leather\",\n" +
                    "      \"credit_id\": \"52fe44c0c3a36847f80a7f49\",\n" +
                    "      \"poster_path\": \"/zS3mDKTGRTQtnfiIRPnjHZM9r0l.jpg\",\n" +
                    "      \"id\": 8848,\n" +
                    "      \"video\": false,\n" +
                    "      \"vote_count\": 1086,\n" +
                    "      \"adult\": false,\n" +
                    "      \"backdrop_path\": \"/h3aTGDGZly8ee9EHxIjIK7UaKf2.jpg\",\n" +
                    "      \"genre_ids\": [\n" +
                    "        53,\n" +
                    "        80,\n" +
                    "        18\n" +
                    "      ],\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"The Bank Job\",\n" +
                    "      \"popularity\": 10.948,\n" +
                    "      \"title\": \"The Bank Job\",\n" +
                    "      \"vote_average\": 6.8,\n" +
                    "      \"overview\": \"Terry is a small-time car dealer trying to leave his shady past behind and start a family. Martine is a beautiful model from Terry's old neighbourhood who knows that Terry is no angel. When Martine proposes a foolproof plan to rob a bank, Terry recognises the danger but realises this may be the opportunity of a lifetime. As the resourceful band of thieves burrows its way into a safe-deposit vault at a Lloyds Bank, they quickly realise that, besides millions in riches, the boxes also contain secrets that implicate everyone from London's most notorious underworld gangsters to powerful government figures, and even the Royal Family. Although the heist makes headlines throughout Britain for several days, a government gag order eventually brings all reporting of the case to an immediate halt.\",\n" +
                    "      \"release_date\": \"2008-03-07\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"character\": \"Frank Martin\",\n" +
                    "      \"credit_id\": \"52fe44e9c3a36847f80b1521\",\n" +
                    "      \"poster_path\": \"/bZKkwfQf4mHYlNbmViNjmAN60zY.jpg\",\n" +
                    "      \"id\": 9335,\n" +
                    "      \"video\": false,\n" +
                    "      \"vote_count\": 1781,\n" +
                    "      \"adult\": false,\n" +
                    "      \"backdrop_path\": \"/71NvWirONP0ZEBfVIdOb3D9GfQ3.jpg\",\n" +
                    "      \"genre_ids\": [\n" +
                    "        28,\n" +
                    "        53,\n" +
                    "        80\n" +
                    "      ],\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"Transporter 2\",\n" +
                    "      \"popularity\": 16.335,\n" +
                    "      \"title\": \"Transporter 2\",\n" +
                    "      \"vote_average\": 6.3,\n" +
                    "      \"overview\": \"Professional driver Frank Martin is living in Miami, where he is temporarily filling in for a friend as the chauffeur for a government narcotics control policymaker and his family. The young boy in the family is targeted for kidnapping, and Frank immediately becomes involved in protecting the child and exposing the kidnappers.\",\n" +
                    "      \"release_date\": \"2005-09-02\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"character\": \"Handsome Rob\",\n" +
                    "      \"credit_id\": \"52fe4517c3a36847f80bbd4b\",\n" +
                    "      \"poster_path\": \"/jNRCNX1uDiUf6mteFxOBn30wL8Q.jpg\",\n" +
                    "      \"id\": 9654,\n" +
                    "      \"video\": false,\n" +
                    "      \"vote_count\": 3047,\n" +
                    "      \"adult\": false,\n" +
                    "      \"backdrop_path\": \"/JyoJwSfcLvfLxCGHUQYlCxvU77.jpg\",\n" +
                    "      \"genre_ids\": [\n" +
                    "        28,\n" +
                    "        80\n" +
                    "      ],\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"The Italian Job\",\n" +
                    "      \"popularity\": 12.879,\n" +
                    "      \"title\": \"The Italian Job\",\n" +
                    "      \"vote_average\": 6.7,\n" +
                    "      \"overview\": \"Charlie Croker pulled off the crime of a lifetime. The one thing that he didn't plan on was being double-crossed. Along with a drop-dead gorgeous safecracker, Croker and his team take off to re-steal the loot and end up in a pulse-pounding, pedal-to-the-metal chase that careens up, down, above and below the streets of Los Angeles.\",\n" +
                    "      \"release_date\": \"2003-05-30\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"character\": \"\",\n" +
                    "      \"credit_id\": \"5a91a602c3a368252900edad\",\n" +
                    "      \"release_date\": \"\",\n" +
                    "      \"vote_count\": 0,\n" +
                    "      \"video\": false,\n" +
                    "      \"adult\": false,\n" +
                    "      \"vote_average\": 0,\n" +
                    "      \"title\": \"The Killer's Game\",\n" +
                    "      \"genre_ids\": [],\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"The Killer's Game\",\n" +
                    "      \"popularity\": 0.654,\n" +
                    "      \"id\": 507241,\n" +
                    "      \"overview\": \"An assassin takes out a contract on himself. When he decides he'd rather live, he must match his skills against the best hit men of the world.\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"crew\": [],\n" +
                    "  \"id\": 976\n" +
                    "}";
            Gson gson = new Gson();
            return gson.fromJson(jsonString, KnownFor.class);
        } catch (Exception e) {
            return null;
        }

    }
}

