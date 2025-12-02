-- -- populating the users table
INSERT INTO users (Username, Email, EncryptedPassword)
VALUES ('admin', 'tempEmail', '$2a$10$Q7DZ9uZBV0o4JuHmTh7xz.XPllXgEuIzvJRPAahDZSxOo2Gf0mb.O');
INSERT INTO users (Username, Email, EncryptedPassword)
VALUES ('user1', 'tempEmail1', '$2a$10$JA7wAHlGmCXoTFkD9rE9h.s3gCGE2f.M37tFxdRdaRh4Hp0NTs2Oe');
INSERT INTO users (Username, Email, EncryptedPassword)
VALUES ('user2', 'tempEmail2', '$2a$10$kvTzFIJ4wKhe8A2NuLbR.uZWe.GX4p.A76EIwoj4w18hMxuuygV8i');
INSERT INTO users (Username, Email, EncryptedPassword)
VALUES ('user3', 'tempEmail3', '$2a$10$IXw73mEWdT1OlaBWO6H9BeKNiKTYU3bo7SjkjO9pbbKpoR7kxVHeG');

-- -- populating the roles table
INSERT INTO roles (name)
VALUES ('ADMIN');
INSERT INTO roles (name)
VALUES ('BASIC');

-- -- assigning the ADMIN role to the admin user
INSERT INTO user_roles (UserID, RoleID)
VALUES (1, 1);
INSERT INTO user_roles (UserID, RoleID)
VALUES (2, 2);
INSERT INTO user_roles (UserID, RoleID)
VALUES (3, 2);
INSERT INTO user_roles (UserID, RoleID)
VALUES (4, 2);

-- -- populating the games table
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Minecraft', 'Mojang Studios', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en_GB', 'https://play-lh.googleusercontent.com/gfNz1N2GNi5piz24IB08RQ4ZGfUnN_kOH8Edhh7uCiotI2P7IBWBXdHzR8gC01ppNnU=w720-h405-rw', 'A creative sandbox game with flexible controls, adjustable difficulty, visual accessibility options, and a calm mode that reduces hostile mobs.', '2011-08-16', '1.21.121', 'Visual support incudes features such as text-to-speech menus and an adjustable UI using mod packs.', 'Audio support includes features such as subtitles and visual sound indicators.', 'Cognitive support features include a creative gameplay that removes pressure.', 'No speech is required.', 'Motor support includes customizable controls and compatibility with assistive devices.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Monument Valley', 'ustwo games', 'PEGI 3', 'https://play.google.com/store/apps/details?id=com.ustwo.monumentvalley&hl=en_GB', 'https://www.nintendo-insider.com/wp-content/uploads/2025/03/monument_valley_banner-1160x653.jpg', 'A relaxing puzzle game with simple tap controls, clean visuals, and minimal text, making it accessible to a wide range of players.', '2014-04-03', 'no version number available', 'There are no features to alter the UI.', 'Music is atmospheric and not necessary to play the game.', 'Relaxed pace and it is easy to undo and retry.', 'No speech is required.', 'There are simple tap/drag controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Among Us', 'Innersloth LLC', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.innersloth.spacemafia&hl=en_GB', 'https://i.ytimg.com/vi/0YKjFoGxbec/maxresdefault.jpg', 'A social deduction game with colorblind mode, clear UI, and simple movement controls, supporting accessible team play.', '2018-06-15', '17.0.1', 'Shapes and name tags are used in tasks to help those with colour blindness, however there is limited visual support for those with low/no vision.', 'Minimal/No reliance on audio cues.', 'Mechanics are simple however social deception adds complexity. ', 'No speech is required as all communication is via a messaging system.', 'There are simple tap/drag controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Candy Crush Saga', 'King', 'PEGI 3', 'https://play.google.com/store/apps/details?id=com.king.candycrushsaga&hl=en_GB', 'https://store-images.s-microsoft.com/image/apps.52397.13510798882606697.1816f804-e7fd-4295-9275-23dec3563baf.2ef7ec2d-2e37-489e-b6ac-b5f5e44d429c?q=90&w=480&h=270', 'A match-3 puzzle game with one-touch controls, optional hints, and bright visuals for easy, accessible play at any pace.', '2012-04-12', '1.315.0.1', 'Visual Support includes a colour blind mode however there is limited visual support for those with low/no vision. ', 'No reliance on audio cues.', 'Repetitive and low pressure gameplay. Some players have reported flashing colours that could affect players with photosensitivity, epilepsy or seizures.', 'No speech is required.', 'Simple swipe controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Call Of Duty®: Mobile', 'Activision Publishing, Inc.', 'PEGI 16', 'https://play.google.com/store/apps/details?id=com.activision.callofduty.shooter&hl=en_GB', 'https://www.gamesatlas.com/images/jch-optimize/ng/images_call-of-duty-mobile_cod-mobile-cover.webp', 'A fast-paced shooter with customizable controls, aim assist, adjustable sensitivity, and UI scaling for improved accessibility.', '2019-10-01', '1.0.52', 'Visual support include features such as a High Contrast Mode.', 'Audio support includes features such as subtitles and directional sound indicators.', 'Cognative support includes features such as tutorials however there is limited support for those with additional cognitive support. There are also photosensitivity/epilepsy/seizure warnings for this game.', 'Voice chat for this game is optional.', 'Motor support includes customizable controls and compatibility with assistive devices.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Pokemon Go', 'Niantic, Inc.', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.nianticlabs.pokemongo&hl=en_GB', 'https://assets.nintendo.eu/image/upload/f_auto,q_auto/v1624945924/NAL/Mobile/Pok%C3%A9mon%20GO/H2x1_SmartDevice_PokemonGo_London_enGB_image1600w.jpg', 'An outdoor adventure game offering large UI icons, vibration cues, and simplified controls to support diverse physical abilities.', '2016-07-06', '0.385.2', 'There are no features to alter the UI.', 'No reliance on audio cues.', 'Gameplay is repetitive and routine friendly. Some players have reported flashing lights that could affect players with photosensitivity, epilepsy or seizures.', 'No speech is required.', 'Simple tap/drag controls to navigate the game, however some players may struggle to aim when trying to catch some pokemon due to distance and movement. Some features require walking certain distances.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Subway Surfers', 'SYBO Games', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.kiloo.subwaysurf&hl=en_GB', 'https://calendar.cx/wp-content/uploads/2025/05/subwaysurfers.png', 'A quick-reaction runner with simple swipe controls, strong color contrast, and optional assist features for ease of play.', '2012-05-024', '3.54.0', 'There is limited visual support for this game.', 'No reliance on audio cues.', 'Simple repetitive game play', 'No speech is required.', 'Simple swipe controls to navigate the game that allows for one handed play, however some players may struggle with the speed of the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Clash of Clans', 'Supercell', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.supercell.clashofclans&hl=en_GB', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQkdYZR9hESsejBEtjUeWUbKr3FsUmjwz6BBw&s', 'A strategy game with slow-paced, tap-based controls and adjustable UI elements, making gameplay manageable for many players.', '2012-08-02', '17.477.15', 'There is limited visual support for this game.', 'No reliance on audio cues.', 'Gameplay is simple and turn-based.', 'No speech is required.', 'Simple tap controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Jigsawscapes® - Jigsaw Puzzles', 'Oakever Games', 'PEGI 3', 'https://play.google.com/store/apps/details?id=jigsaw.puzzle.game.banana&hl=en_GB', 'https://m.media-amazon.com/images/I/A1bxq4y1RXL.png', 'A relaxing jigsaw puzzle app with zoom options, piece outlines, and soothing visuals designed for stress-free, accessible play.', '2023-04-26', '4.2.2', 'Visual support include features such as adjustable contrast/background, however there is limited visual support for players with low/no vision.', 'No reliance on audio cues.', 'Gameplay is simple, relaxing and not timed.', 'No speech is required.', 'Simple tap/drag controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('My Oasis: Relaxing, Satisfying', 'JUNEiNTER Co., Ltd.', 'PEGI 3', 'https://play.google.com/store/apps/details?id=com.buffstudio.myoasis&hl=en_GB', 'https://cdn.aptoide.com/imgs/6/f/2/6f234be40d6930c031f8bcc99275416c_fgraphic.png', 'A calming idle game with soft visuals, minimal input, and gentle soundscapes that support relaxation and low-pressure interaction.', '2025-10-28', 'no version number available', 'There are no features to alter the UI.', 'No reliance on audio cues.', 'Gameplay is simple, calming and non-challenging.', 'No speech is required.', 'Simple tap controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Altos Odyssey', 'Noodlecake', 'PEGI 3', 'https://play.google.com/store/apps/details?id=com.noodlecake.altosodyssey&hl=en_GB', 'https://i.ytimg.com/vi/GcB7c1eHb6M/hqdefault.jpg', 'A serene endless-runner with one-touch mechanics, high-contrast visuals, and an optional zen mode for stress-free play.', '2018-02-021', '1.3.0', 'There is limited visual support for this game', 'No reliance on audio cues.', 'Gameplay is simple with a lack of complex rules or time limits.', 'No speech is required.', 'Simple tap controls to navigate the game, however some players may struggle with the timing required for jumps.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('KAMI 2', 'State of Play', 'PEGI 3', 'https://play.google.com/store/apps/details?id=com.stateofplaygames.kami2&hl=en_GB', 'https://images.squarespace-cdn.com/content/v1/57b5d1f0d482e93404415213/1490788832173-0RY3VJV0MOS89WICFOGB/image-asset.jpeg', 'A visually clean puzzle game with simple tap interactions and color-based challenges designed with calm, focused gameplay in mind.', '2017-03-30', '1.9.3', 'The UI has highly contrasting colours however there is limited visual support for players with low/no vision.', 'No reliance on audio cues.', 'Puzzles are slow and untimed.', 'No speech is required.', 'Simple tap controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Sky: Children of the Light', 'thatgamecompany inc', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.tgc.sky.android&hl=en_GB', 'https://adventuregamers.com/wp-content/uploads/images/screenshots/50122/-header.jpg', 'An emotional adventure with gentle controls, scalable visuals, cooperative play, and accessibility-friendly social mechanics.', '2019-07-18', '0.31.2', 'There are no features to alter the UI.', 'There are some audio cues however the game is playable with no audio', 'Some players have reported flashing lights that could affect players with photosensitivity, epilepsy or seizures.', 'No speech is required as emotes are used for communication.', 'Simple movement/flight controls to navigate the game, however some players may struggle with the precision required for certain actions.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Stardew Valley', 'ConcernedApe', 'PEGI 12', 'https://play.google.com/store/apps/details?id=com.chucklefish.stardewvalley&hl=en_GB', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRzag19Vq5eT2IZPNZZYU-PxjM1xLc6mEJEnQ&s', 'A peaceful farming RPG offering remappable controls, no strict time pressure, and a play-at-your-own-pace structure ideal for accessibility.', '2019-03-14', '1.6.9', 'Limited visual support is available for the app version of this game.', 'No reliance on audio cues.', 'Gameplay is non-linear and self-paced.', 'No speech is required.', 'Controls are simple and compatible with assistive devices.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('A Blind Legend', 'DOWINO', 'PEGI 3', 'https://a-blind-legend.en.aptoide.com/app', 'https://images.launchbox-app.com/4580cd6a-d441-4745-ac9d-4cc3df4df468.png', 'A fully audio-based adventure designed for blind and visually impaired players, using binaural sound for navigation instead of visuals.', '2015-06-10', '1.2.4', 'Fully accessible, and designed for, players with no/low vision.', 'Fully reliant on audio cues.', 'Gameplay is story structured.', 'No speech is required.', 'Simple tap and swipe controls to navigate the game.');

-- -- population gallery table
-- minecraft images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (1, 'https://static.wikia.nocookie.net/minecraft_gamepedia/images/6/60/Survival.png/revision/latest/scale-to-width-down/1200?cb=20220702034959', 'First-person view in Minecraft showing a grassy hillside made of blocky dirt and grass layers under a clear blue sky. The player’s hand and hotbar are visible at the bottom of the screen, indicating survival mode with full health and hunger bars.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (1, 'https://minecraft.wiki/images/thumb/Survival.png/1200px-Survival.png?b8898', 'First-person view in Minecraft showing a player standing beside a river bordered by grassy banks and dense trees, including oak and birch. The sky is bright with scattered clouds, and the player’s survival mode interface with full health and hunger bars is visible at the bottom.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (1, 'https://terracoders.com/sites/default/files/inline-images/MC.png', 'First-person view in Minecraft showing a player holding a diamond sword and shield, looking toward a village built on swampy terrain beside a mountain. The scene includes wooden and stone buildings, one with purple blocks and another tall stone tower connected by a long bridge. The player’s survival interface shows health, hunger, and experience levels.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (1, 'https://edusupport.minecraft.net/hc/user_images/9tVBjYqlMS-O3qbKgN5kEQ.png', 'Minecraft first-person view showing a player standing on a wooden dock in front of a house with glass windows and a wooden door. The player holds a shield and enchanted pickaxe, and game subtitles like Pressure Plate clicks and Door creaks appear on the screen.');
-- monument valley images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (2, 'https://www.monumentvalleygame.com/media/trailer-landscape-poster-fe7637d3.jpg', 'A surreal, pastel-colored 3D puzzle scene featuring geometric architecture floating above calm water. A small figure in purple stands on an impossible structure leading to a tower with a red flag, illuminated by soft pink and purple light.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (2, 'https://blog.richersounds.com/wp-content/uploads/2022/10/MV_FS_Thief.0.0.jpeg', 'A dreamy, pastel-colored scene of floating architectural structures with arches, stairs, and platforms rising above the clouds. A small white-robed figure and a dark crow-like figure stand on separate paths within the surreal, Escher-inspired landscape.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (2, 'https://xboxwire.thesourcemediaassets.com/sites/2/2025/07/MV3_Screenshots_1920x1080_8-b2621aac4320d6255ff1.jpg', 'A vibrant, dreamlike scene showing a golden temple-like structure with arches, towers, and orange trees surrounded by calm turquoise water. Small characters explore the architecture, and a large shadow of a whale can be seen beneath the surface.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (2, 'https://assetsio.gnwcdn.com/-1579272639622.jpg?width=1200&height=900&fit=crop&quality=100&format=png&enable=upscale&auto=webp', 'Minimalist, surreal scene with a white-hooded figure on angular paths. Three black figures on stairs in pastel architecture inspire mystery and curiosity.');
-- among us images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (3, 'https://www.innersloth.com/wp-content/uploads/2021/06/Guardian-Angel.png', 'Six colorful characters in spacesuits gather in an electrical room in the game Among Us. Wires and tasks are visible. A green task bar at the top shows progress. The atmosphere is tense and strategic.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (3, 'https://www.innersloth.com/wp-content/uploads/2021/06/Lobby-1.png', 'Game lobby of "Among Us" showing 12 colorful characters, each labeled with a color, beside floating computers. Game settings are listed on the left.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (3, 'https://oyster.ignimgs.com/mediawiki/apis.ign.com/among-us/e/e6/Screenshot_%28331%29.png?width=1280', 'A red character stands in the center of a sci-fi themed game environment labeled Storage. A task bar at the top shows progress. The atmosphere feels strategic and mysterious.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (3, 'https://www.esports.net/wp-content/uploads/2024/02/AmongUsGameplay1.webp', 'Four colorful astronaut-like characters gather around a round table in a virtual cafeteria setting from the game "Among Us." The floor is gray with shadows.');
-- candy crush saga images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (4, 'https://cdn-www.bluestacks.com/bs-images/Candy-Crush-Saga_Tips-Tricks_EN_6.jpg', 'Game interface with colorful candies in a puzzle grid. Left sidebar shows moves left, power-ups, and points. Vibrant and playful design.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (4, 'https://media-cldnry.s-nbcnews.com/image/upload/t_nbcnews-fp-1200-630,f_auto,q_auto:best/newscms/2014_04/133241/candy-crush-saga-screenshot-01.jpg', 'Colorful candy puzzle game screen with various candies in rows. Score: 4520, Moves left: 33. Bright and playful, evokes a fun, engaging mood.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (4, 'https://i.guim.co.uk/img/media/cce6a7148687a60c19c60f35e1d0dde4fb755775/0_0_1749_1050/master/1749.jpg?width=465&dpr=1&s=none&crop=none', 'placeholder');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (4, 'https://joywikia-ccs.weebly.com/uploads/2/1/2/5/21259908/1163467_orig.png', 'Colorful Candy Town game map with a winding path of numbered circles, stars, and candy-themed decorations. Cheerful and playful atmosphere.');
-- call of duty mobile images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (5, 'https://images.squarespace-cdn.com/content/v1/58af450eb3db2b0582612f1d/1574235600878-89O2J69L16OZAKSH60DO/Screenshot+2019-11-20+at+9.39.42.png', 'First-person shooter game scene with a rifle aimed at a street lined with houses and parked cars. Game HUD displays objectives and controls.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (5, 'https://www.gamespot.com/a/uploads/original/172/1720905/3545065-br%20001%20logo.jpg', 'Video game scene from Call of Duty Mobile showing soldiers in combat amidst a cherry blossom landscape, with helicopters and gunfire. Energetic and intense mood.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (5, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRj3ev3iHcUemu8IiUSMUy-2Te01-G6Ibu77g&s', 'Intense battle scene from Call of Duty Mobile. Soldiers engage in a firefight near a yellow house, with a drone overhead and explosions in the background, creating a dynamic and action-packed atmosphere.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (5, 'https://static0.thegamerimages.com/wordpress/wp-content/uploads/2019/09/Mobile-Cod.jpg?q=50&fit=crop&w=825&dpr=1.5', 'Two armed soldiers engage in battle near a Mediterranean-style building with a large explosion.');
-- pokemon go images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (6, 'https://techcrunch.com/wp-content/uploads/2017/12/pokemon-go-weather.png', 'Screenshot from a mobile game showing changing weather effects: sunny, cloudy, and rainy settings with a character in a digital map environment.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (6, 'https://eegamesmart.co.uk/g/s/2375/pokemon-go_2911_2375.jpg', 'A collage of four Pokémon GO gameplay images. Players encounter various Pokémon: Doduo, Metapod, Smeargle, and Sandshrew, in vibrant outdoor settings.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (6, 'https://sm.pcmag.com/pcmag_uk/cover/p/pokemon-go/pokemon-go-for-iphone_ptuk.jpg', 'A Pokémon Go game screen shows an avatar surrounded by virtual creatures on a map. PokéStops are visible, creating a lively and adventurous atmosphere.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (6, 'https://studio.uxpincdn.com/studio/wp-content/uploads/2016/07/image08.jpg.webp', 'Split-screen image showing Pokémon GO gameplay. Left: Growlithe on a sidewalk. Center: Voltorb on grass. Right: Nidoran♂ near a street. Bright, sunny day.');
-- subway surfers images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (7, 'https://marksangryreview.com/wp-content/uploads/2018/12/Gameplay-of-Subway-Surfers.jpg', 'Colorful game scene of a character running on train tracks, collecting coins. A red tunnel and greenery are in the background, with an approaching train.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (7, 'https://cdn.revid.ai/subway_surfers/green_surfer_poster.webp', 'A skateboarder rides along train tracks in a spooky, Halloween-themed village. Glowing pumpkins line the path, and houses have lit windows.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (7, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSllPja2FGrlbtBtcTbBvmlX-g7be6JSZ0LDw&s', 'A video game scene shows a character named Jake snowboarding along train tracks in a festive city, with snow falling and bright lights glowing.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (7, 'https://bbsstatic.oppo.com/upload/image/front/thread/20240828/0586081860521903321/1673021720803409924/1673021720803409924.jpg', 'A vibrant game screen from Subway Surfers featuring a character in a cap running from a train along tracks in a cityscape with obstacles ahead. Bright and dynamic.');
-- clash of clans images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (8, 'https://static1.squarespace.com/static/58af450eb3db2b0582612f1d/t/58af55d99a6bf733a4094091/1487885785148/1000w/', 'A mobile game screen shows a village layout with huts, cannons, and resources on green grass. Buttons for Attack and Shop appear on the edges.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (8, 'https://en.docs.balancy.dev/game_templates/clash_of_clans_base.jpg', 'A vibrant village scene from a mobile strategy game showing small buildings, cannons, resources, and character icons on a grassy grid layout.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (8, 'https://miro.medium.com/1*KP_tGPqayAmu1oqPRRoc8Q.png', 'A character with red hair warns of a goblin attack in a grassy village scene. She urges building a cannon. Icons show resources and a shop button.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (8, 'https://assets.newsweek.com/wp-content/uploads/2025/08/674748-clash-clans-2.png', 'A vibrant fantasy village scene from a game, features a green goblin in front, a large armored figure, and buildings surrounded by troops and resources.');
-- jigsawscapes images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (9, 'https://files.backbon3.com/cdn-cgi/image/width=1920,height=1080,fit=scale-down/content/2024/jigsawscapes-wallpaper-1080.jpg', 'A colorful puzzle depicting a rooster and hen with chicks in a vibrant farm scene. Sunflowers, blue pots, and a scenic river are visible. Title reads Puzzle Game for Adults. Some pieces are missing, adding an interactive feel.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (9, 'https://play-lh.googleusercontent.com/4cYkmmaPpuHZmviWi5XaaCVMM1bdv2IoYcgtMj6naawSIIZdGbNeJVQQ-2k1DW2lpq8=w526-h296-rw', 'Jigsaw puzzle of a serene landscape with a windmill by a river, red and pink tulips in the foreground, blue sky. Text reads Immersive Experiences.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (9, 'https://img.tapimg.net/market/images/57fafcad06a84af27e4d95ffae491772.jpg?imageView2/2/w/720/h/720/q/80/format/jpg/interlace/1/ignore-error/1&t=1', 'Hand holds a puzzle piece, close to completing an image of a vibrant blue kingfisher on a branch. "Jigsawscapes Jigsaw Puzzle" on wooden background.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (9, 'https://games.lol/wp-content/uploads/2023/03/jigsawscapes-for-pc.jpg', 'A tablet screen displays a digital jigsaw puzzle game with a vase of flowers. A finger presses Play. The background shows various nature images. A banner reads Easy to Play.');
-- my oasis images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (10, 'https://play-lh.googleusercontent.com/_tA4d47ZXTaj7TDrM5Beb6kNcbPVsAnF1CFT8D7UvRkV5EH1v_QWz2Dr7p2R4909NlY=w526-h296-rw', 'Low-poly scenic landscape with pink trees, a pond with lily pads, a panda, and floating island under sun rays. Text reads My Oasis.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (10, 'https://play-lh.googleusercontent.com/mKyzCp_tBtWF94vp0nDN5UhImNWpy-vnGfro4ZXWz4Ud8Vd1rDTKNqPOSbY0yvvQqLc=w526-h296-rw', 'A serene, colorful landscape features a lush green island with a central waterfall, surrounded by diverse trees and soft rolling hills. A bright rainbow arcs across the clear blue sky. The screen displays playful icons and musical notes, suggesting an interactive, game-like environment. The mood is peaceful and whimsical.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (10, 'https://play-lh.googleusercontent.com/_tqXZhaWrRyyZnm0asI0Du5-B5S8ZAkiTX4OKleh4suhTTH7IUqTatlz89RjNLiiIos=w526-h296-rw', 'A stylized, low-poly desert oasis with animals by a pond, surrounded by cacti and palm trees. Hot air balloons and a whale in the colorful sky.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (10, 'https://play-lh.googleusercontent.com/iwISgD7JdB2z9X_3c6t32tRFSK4noH0tWA6x2CL0NVgm1oAbtKBzLV9Q-s9IpWleNw=w526-h296-rw', 'Colorful game interface showing animated hearts over a virtual oasis landscape with desert hills, palm trees, and calm water. Icons and notes appear at the bottom.');
-- altos odyssey images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (11, 'https://lh5.googleusercontent.com/proxy/QJLjIeFGQaWrSb1E5dGJmIJ2P4qCbVvTa1MieRdl5HEXfDBVZCt7t1Ojzk0iGvIaAEr_md217tDDxgZp22zK_Q6u-Hvo3cTYcJ1wCXIxIWhOfXrCM3foHJ5jPzXa1uBFhw', 'Desert landscape at sunset with silhouettes of huts, cacti, and hot air balloons against a glowing sun. Warm, serene atmosphere conveyed.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (11, 'https://lh5.googleusercontent.com/proxy/-GQOUKkc5gELrs5cDy6DBnigFuZoBaVRwEGHbY5E133RWFEl3JS8qcfJLU3ysHFXSFyeHgnjcBSoWtp9VlTsDHEZVj-gadZ8ymN0dLaGSyXmFHNym5aH9TisCtBafjYzOw', 'A desert landscape in a video game, with hot air balloons, a setting sun, and distant mountains. A character and lights adorn a cable. Calm and adventurous.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (11, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUdD5wefQqfEpWyQKVftMraJoqAJjhd3Lpdw&s', 'A silhouette of a fairytale castle on a hill at sunset, surrounded by trees and hot air balloons, with a crescent moon and stars in a purple-pink sky.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (11, 'https://play-lh.googleusercontent.com/jH3s-0No7pqOtHW81UEoqJ4qpafdhRJSdDXLyuz9KoCLAMJVZ8A_n61dxU6xaRWQ-Xk=w526-h296-rw', 'Minimalist landscape with a glowing sun, rolling hills, and silhouetted structures. A waterboarder glides, creating splashes. Calm, adventure vibe.');
-- kami 2 images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (12, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTF1fsdr36vWQdORCcu_IEjzPgp3lFXJMISjQ&s', 'Colorful hexagonal and geometric patterns arranged in three sets of four, each topped by Journey. Each set has a Perfect badge below.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (12, 'https://noodlecake.com/wp-content/uploads/2025/07/Kami2_Screenshots_1920x1080_3.png', 'Colorful geometric pattern with overlapping green and yellow hexagons on a light blue background. The pattern is dynamic and abstract, with a playful tone.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (12, 'https://noodlecake.com/wp-content/uploads/2025/07/Kami2_Screenshots_1920x1080_2.png', 'Abstract geometric pattern with pink, yellow, and white triangular shapes. A small yellow star-like shape is centered on a diagonal pink strip.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (12, 'https://games.lol/wp-content/uploads/2022/06/kami-2-pc-gameplay-on-pc-500x279.jpg', 'Geometric puzzle game interface with two abstract designs, one marked Perfect! in gold. The scene conveys an achievement or success.');
-- sky children of the light images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (13, 'https://play-lh.googleusercontent.com/WBIFZVGLEFruDldtY5hNZtzXGj_lESdG432F_bkYaIJLFRIMQCzZB9tmVeQFnX0skQ=w526-h296-rw', 'Four stylized characters with glowing features sit around a bright campfire on a grassy field under a starry night, creating a cozy, magical atmosphere.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (13, 'https://www.gamespot.com/a/uploads/original/1575/15759911/3565917-img_5237.jpg', 'A robed figure stands in a dimly lit temple, facing an altar surrounded by glowing candles. The scene feels mystical and serene, with rays of light.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (13, 'https://d2duuy9yo5pldo.cloudfront.net/thatgamecompany/5724faa2-266a-41ef-932a-4ca8da0bdcf3-2t.png', 'A character in a blue cape approaches two mystical portals set in rocky structures, with one glowing blue and the other emitting a pink light. The scene is tranquil.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (13, 'https://play-lh.googleusercontent.com/gCZKFl5YILlxnGme9gYG3VChiQ68hbLoWJkBP1DGGaCrgrGRu4Ihydj_91Pqxm6RQg=w526-h296-rw', 'Two glowing figures in white and orange capes fly over a red landscape, with a dark, menacing silhouette in the green misty background. Mystical and adventurous mood.');
-- stardew valley images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (14, 'https://play-lh.googleusercontent.com/K3iTDHZUEWPARrou2r_30gaXIoKXZfirTkNpY2I6YvGGoNfPLhXDIvJGd1cGQ-9UxA=w526-h296-rw', 'A pixel-art scene from a farming RPG game shows characters on a wooden dock with a house and purple boat. Overlay reads, The smash hit farming RPG comes to Mobile.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (14, 'https://www.indiegamewebsite.com/wp-content/uploads/2018/10/unnamed.png', 'Pixel art of a character standing outside a wooden cottage in a farm setting from a game. Grass and a mailbox are visible. The visual has a cozy feel.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (14, 'https://www.stardewvalley.net/wp-content/uploads/2018/10/SDV_iPhone_10_half.png', 'Pixel art scene from a game: A person in a hat fishes next to a pond in a fall setting, with mushrooms and a cabin nearby. Autumn leaves float in the water.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (14, 'https://miro.medium.com/v2/resize:fit:1400/1*jxWtCZIrOXxTnQUT52u-kQ.jpeg', 'Pixelated farm scene from a video game shows two farmers in hats tending crops near a wooden house, well, and beehives. The setting is bright and cheerful.');
-- a blind legend images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (15, 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/437530/header.jpg?t=1750429962', 'Cover art for "A Blind Legend" game. The title is boldly displayed, featuring a sword as the ''I''. Dark, shadowy figures emerge in the background. Mysterious and intense atmosphere.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (15, 'https://www.showmetech.com.br/wp-content/uploads/2014/08/A-Blind-Legend-como-funciona.png', 'Illustrated instructions on using a smartphone game: 1) Move, 2) Turn, 3) Strike, 4) Block, 5) Repel, 6) Ouch! Gestures guide interactive actions.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (15, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/437530/page_bg_raw.jpg?t=1750429962', 'A person wearing headphones and holding a phone appears immersed in music, eyes closed. Mysterious shadowy figures move through a dark, foggy environment. Mood is enigmatic and introspective.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (15, 'https://gpstatic.com/acache/29/92/1/uk/s1-3aba7a53bca1f519c1b1e1494c1f1e89.jpg', 'A headset icon on a dark, misty background, accompanied by text reading, A Blind Legend is an audio game. Make sure you have properly equipped your headset.');

-- -- populating reviews table
INSERT INTO reviews (UserID, GameID, HearingRating, VisionRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (2,1,5,3,4,4,5,'Minecraft offers a fantastic sensory experience with its blocky visuals and immersive sound design. The gameplay is engaging and allows for creativity. The customisable control features allowed me to adapt the game to my needs, making it accessible and enjoyable.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisionRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (2,15, 0,5,4,4,5,'A Blind Legend provides an exceptional auditory experience, making it accessible for visually impaired players. The sound design is immersive, and the gameplay is intuitive. The game effectively combines audio cues with engaging mechanics, creating a unique adventure. The motor controls are manageable, allowing for an enjoyable experience.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisionRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (2, 6, 5,3, 4,3,3,'Pokemon Go offers a fun and interactive experience with its augmented reality features. The sound effects enhance the gameplay, and the visual design is appealing. I struggled a bit with the motor controls, but overall, it was an enjoyable game that encouraged outdoor activity.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisionRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (2, 9,5,3,5,4,4,'Jigsawscapes provides a relaxing and enjoyable puzzle experience. The sound effects are soothing, and the visual design is clear. The gameplay is straightforward, and the motor controls are easy to manage. It''s a great game for unwinding and stimulating the mind.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisionRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (2, 5, 4,2,2,3,3,'Call of Duty Mobile delivers an intense gaming experience with its realistic sound effects and graphics. I found the speed and complexity of the gameplay challenging, particularly with the motor controls. However, the game is engaging and offers a variety of modes to keep players interested.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisionRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (2, 13, 2,2,2,3,2,'Sky Children of the Light offers a visually stunning experience with its beautiful landscapes and calming soundscapes. However , I found the gameplay to be slow-paced and the motor controls somewhat challenging. While the game is visually appealing, it may not provide enough stimulation for all players.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisionRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (3, 15,0,0,2,2, 1,'I could not fully enjoy A Blind Legend due to its heavy reliance on auditory cues, as someone with hearing loss this presented significant challenges.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisionRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (3, 13, 2,5,3,4,3,'The visual design of Sky Children of the Light is stunning, however, the auditory elements were difficult for me to engage with due to my hearing impairment. The gameplay was enjoyable, but I found some of the controls challenging.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisionRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (3, 3, 5,5,4,4,5,'Really fun game! There is no reliance on audio cues, making it accessible for players with hearing impairments.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisionRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (3, 7, 5,4,4,5,5,'No reliance on audio cues, making it accessible for players with hearing impairments. The vibrant visuals and engaging gameplay make Subway Surfers a great choice for all players.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisionRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (4,14, 5,5,5,4,5,'Stardew Valley is an excellent game that offers a rich sensory experience. The gameplay is engaging and allows for creativity. The motor controls are manageable, making it accessible for players, like myself, with motor impairments.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisionRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (4, 3, 5,5,4,3,3,'Gameplay was fun and engaging, but I found some of the motor controls challenging due to my disability. However, the game is still enjoyable and offers a great multiplayer experience, even without audio.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisionRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (4,13,3,5,4,2,3,'I struggled to play this game due to the motor controls and audio cues for dangerous situations. However, the visual design is stunning.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisionRating, CognitiveRating, MotorRating, GameplayRating, ReviewText, flagged)
VALUES (4, 15,0,5,3,2,2,'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', TRUE);


-- -- populating user_favorites table
INSERT INTO user_favorites (UserID, GameID)
VALUES (2,1);
INSERT INTO user_favorites (UserID, GameID)
VALUES (2,14);
INSERT INTO user_favorites (UserID, GameID)
VALUES (3,2);
INSERT INTO user_favorites (UserID, GameID)
VALUES (3,1);
INSERT INTO user_favorites (UserID, GameID)
VALUES (3,9);
INSERT INTO user_favorites (UserID, GameID)
VALUES (3,4);
INSERT INTO user_favorites (UserID, GameID)
VALUES (3,8);
INSERT INTO user_favorites (UserID, GameID)
VALUES (3,10);
INSERT INTO user_favorites (UserID, GameID)
VALUES (4,14);

-- -- populating tags table
INSERT INTO tags (TagName, Type)
VALUES('Visual Support', 'Accessibility');
INSERT INTO tags (TagName, Type)
VALUES('Cognitive Support', 'Accessibility');
INSERT INTO tags (TagName, Type)
VALUES('Verbal Support', 'Accessibility');
INSERT INTO tags (TagName, Type)
VALUES('Audio Support', 'Accessibility');
INSERT INTO tags (TagName, Type)
VALUES('Motor Support', 'Accessibility');
INSERT INTO tags (TagName, Type)
VALUES('Action', 'Genre');
INSERT INTO tags (TagName, Type)
VALUES('Adventure', 'Genre');
INSERT INTO tags (TagName, Type)
VALUES('Puzzle', 'Genre');
INSERT INTO tags (TagName, Type)
VALUES('Strategy', 'Genre');
INSERT INTO tags (TagName, Type)
VALUES('Casual', 'Genre');
INSERT INTO tags (TagName, Type)
VALUES('Role-Player', 'Genre');
INSERT INTO tags (TagName, Type)
VALUES('Simulation', 'Genre');
INSERT INTO tags (TagName, Type)
VALUES('Arcade', 'Genre');
INSERT INTO tags (TagName, Type)
VALUES('Sandbox / Open World', 'Genre');
INSERT INTO tags (TagName, Type)
VALUES('Multiplayer / Online', 'Genre');

-- -- populating game_tags table
-- minecraft tags
INSERT INTO game_tags (GameID, TagID)
VALUES (1, 1);
INSERT INTO game_tags (GameID, TagID)
VALUES (1, 2);
INSERT INTO game_tags (GameID, TagID)
VALUES (1, 3);
INSERT INTO game_tags (GameID, TagID)
VALUES (1, 4);
INSERT INTO game_tags (GameID, TagID)
VALUES (1, 5);
INSERT INTO game_tags (GameID, TagID)
VALUES (1, 14);
INSERT INTO game_tags (GameID, TagID)
VALUES (1, 12);
INSERT INTO game_tags (GameID, TagID)
VALUES (1, 7);
INSERT INTO game_tags (GameID, TagID)
VALUES (1, 15);
INSERT INTO game_tags (GameID, TagID)
VALUES (1, 6);
-- monument valley tags
INSERT INTO game_tags (GameID, TagID)
VALUES (2, 2);
INSERT INTO game_tags (GameID, TagID)
VALUES (2, 3);
INSERT INTO game_tags (GameID, TagID)
VALUES (2, 4);
INSERT INTO game_tags (GameID, TagID)
VALUES (2, 5);
INSERT INTO game_tags (GameID, TagID)
VALUES (2, 8);
INSERT INTO game_tags (GameID, TagID)
VALUES (2, 7);
-- among us tags
INSERT INTO game_tags (GameID, TagID)
VALUES (3, 3);
INSERT INTO game_tags (GameID, TagID)
VALUES (3, 4);
INSERT INTO game_tags (GameID, TagID)
VALUES (3, 5);
INSERT INTO game_tags (GameID, TagID)
VALUES (3, 15);
INSERT INTO game_tags (GameID, TagID)
VALUES (3, 9);
INSERT INTO game_tags (GameID, TagID)
VALUES (3, 10);
-- candy crush saga tags
INSERT INTO game_tags (GameID, TagID)
VALUES (4, 2);
INSERT INTO game_tags (GameID, TagID)
VALUES (4, 3);
INSERT INTO game_tags (GameID, TagID)
VALUES (4, 4);
INSERT INTO game_tags (GameID, TagID)
VALUES (4, 5);
INSERT INTO game_tags (GameID, TagID)
VALUES (4, 8);
INSERT INTO game_tags (GameID, TagID)
VALUES (4, 10);
INSERT INTO game_tags (GameID, TagID)
VALUES (4, 6);
-- call of duty mobile tags
INSERT INTO game_tags (GameID, TagID)
VALUES (5, 1);
INSERT INTO game_tags (GameID, TagID)
VALUES (5, 3);
INSERT INTO game_tags (GameID, TagID)
VALUES (5, 4);
INSERT INTO game_tags (GameID, TagID)
VALUES (5, 5);
INSERT INTO game_tags (GameID, TagID)
VALUES (5, 15);
INSERT INTO game_tags (GameID, TagID)
VALUES (5, 6);
INSERT INTO game_tags (GameID, TagID)
VALUES (5, 9);
INSERT INTO game_tags (GameID, TagID)
VALUES (5, 11);
-- pokemon go tags
INSERT INTO game_tags (GameID, TagID)
VALUES (6, 2);
INSERT INTO game_tags (GameID, TagID)
VALUES (6, 3);
INSERT INTO game_tags (GameID, TagID)
VALUES (6, 4);
INSERT INTO game_tags (GameID, TagID)
VALUES (6, 7);
INSERT INTO game_tags (GameID, TagID)
VALUES (6, 11);
INSERT INTO game_tags (GameID, TagID)
VALUES (6, 10);
INSERT INTO game_tags (GameID, TagID)
VALUES (6, 12);
INSERT INTO game_tags (GameID, TagID)
VALUES (6, 15);
-- subway surfers tags
INSERT INTO game_tags (GameID, TagID)
VALUES (7, 2);
INSERT INTO game_tags (GameID, TagID)
VALUES (7, 3);
INSERT INTO game_tags (GameID, TagID)
VALUES (7, 4);
INSERT INTO game_tags (GameID, TagID)
VALUES (7, 13);
INSERT INTO game_tags (GameID, TagID)
VALUES (7, 10);
INSERT INTO game_tags (GameID, TagID)
VALUES (7, 7);
-- clash of clans tags
INSERT INTO game_tags (GameID, TagID)
VALUES (8, 2);
INSERT INTO game_tags (GameID, TagID)
VALUES (8, 3);
INSERT INTO game_tags (GameID, TagID)
VALUES (8, 4);
INSERT INTO game_tags (GameID, TagID)
VALUES (8, 5);
INSERT INTO game_tags (GameID, TagID)
VALUES (8, 9);
INSERT INTO game_tags (GameID, TagID)
VALUES (8, 15);
INSERT INTO game_tags (GameID, TagID)
VALUES (8, 6);
-- jigsawscapes tags
INSERT INTO game_tags (GameID, TagID)
VALUES (9, 2);
INSERT INTO game_tags (GameID, TagID)
VALUES (9, 3);
INSERT INTO game_tags (GameID, TagID)
VALUES (9, 4);
INSERT INTO game_tags (GameID, TagID)
VALUES (9, 5);
INSERT INTO game_tags (GameID, TagID)
VALUES (9, 8);
INSERT INTO game_tags (GameID, TagID)
VALUES (9, 10);
-- my oasis tags
INSERT INTO game_tags (GameID, TagID)
VALUES (10, 2);
INSERT INTO game_tags (GameID, TagID)
VALUES (10, 3);
INSERT INTO game_tags (GameID, TagID)
VALUES (10, 4);
INSERT INTO game_tags (GameID, TagID)
VALUES (10, 5);
INSERT INTO game_tags (GameID, TagID)
VALUES (10, 12);
INSERT INTO game_tags (GameID, TagID)
VALUES (10, 10);
-- altos odyssey tags
INSERT INTO game_tags (GameID, TagID)
VALUES (11, 3);
INSERT INTO game_tags (GameID, TagID)
VALUES (11, 4);
INSERT INTO game_tags (GameID, TagID)
VALUES (11, 6);
INSERT INTO game_tags (GameID, TagID)
VALUES (11, 10);
INSERT INTO game_tags (GameID, TagID)
VALUES (11, 13);
-- kami 2 tags
INSERT INTO game_tags (GameID, TagID)
VALUES (12, 2);
INSERT INTO game_tags (GameID, TagID)
VALUES (12, 3);
INSERT INTO game_tags (GameID, TagID)
VALUES (12, 4);
INSERT INTO game_tags (GameID, TagID)
VALUES (12, 5);
INSERT INTO game_tags (GameID, TagID)
VALUES (12, 8);
INSERT INTO game_tags (GameID, TagID)
VALUES (12, 10);
-- sky children of the light tags
INSERT INTO game_tags (GameID, TagID)
VALUES (13, 3);
INSERT INTO game_tags (GameID, TagID)
VALUES (13, 5);
INSERT INTO game_tags (GameID, TagID)
VALUES (13, 7);
INSERT INTO game_tags (GameID, TagID)
VALUES (13, 12);
INSERT INTO game_tags (GameID, TagID)
VALUES (13, 15);
-- stardew valley tags
INSERT INTO game_tags (GameID, TagID)
VALUES (14, 2);
INSERT INTO game_tags (GameID, TagID)
VALUES (14, 3);
INSERT INTO game_tags (GameID, TagID)
VALUES (14, 4);
INSERT INTO game_tags (GameID, TagID)
VALUES (14, 5);
INSERT INTO game_tags (GameID, TagID)
VALUES (14, 12);
INSERT INTO game_tags (GameID, TagID)
VALUES (14, 11);
INSERT INTO game_tags (GameID, TagID)
VALUES (14, 7);
-- a blind legend tags
INSERT INTO game_tags (GameID, TagID)
VALUES (15, 1);
INSERT INTO game_tags (GameID, TagID)
VALUES (15, 3);
INSERT INTO game_tags (GameID, TagID)
VALUES (15, 5);
INSERT INTO game_tags (GameID, TagID)
VALUES (15, 6);
INSERT INTO game_tags (GameID, TagID)
VALUES (15, 7);

-- -- populating disscussions table
INSERT INTO discussions (UserID, DiscussionTitle, DiscussionText)
VALUES (1, 'Improvements to the voice command system', 'Do you use the voice command system in PlayAble? We want to hear what improvements would you suggest to make it better for all users!');
INSERT INTO discussions (UserID, DiscussionTitle, DiscussionText)
VALUES (2, 'What are the best games for motor/dexterity impairments?', 'I am looking for mobile games that are accessible for players with motor or dexterity impairments. Any recommendations?');
INSERT INTO discussions (UserID,DiscussionTitle, DiscussionText)
VALUES (4, 'Struggling to find games that suit my needs', 'I have a combination of audio and motor impairments, and I find it hard to find games that are both fun and accessible. Any suggestions?');

-- -- populating replies table
INSERT INTO replies (DiscussionID, UserID, ReplyText)
VALUES (1, 4, 'I use the voice command system often and I cant really suggest any improvements. It works well for me as is!');
INSERT INTO replies (DiscussionID, UserID, ReplyText)
VALUES (2, 4, 'I really enjoy Stardew Valley. It has simple motor controls and great visual support options.');
INSERT INTO replies (DiscussionID, UserID, ReplyText)
VALUES (2, 3, 'Oh and I would also reccomend Minecraft! Its very accessible and has a lot of options to customize the controls to your needs.');
INSERT INTO replies (DiscussionID, UserID, ReplyText)
VALUES (3, 2, 'Have you tried Monument Valley? Its a great puzzle game! The motor controls are simple and it has good visual support features and no audio cues to worry about.');

-- -- populating faq table
INSERT INTO faq (Question, Answer)
VALUES ('How do I navigate the PlayAble website using the voice command system?', 'To navigate the PlayAble website using the voice command system, simply activate the voice command feature and speak your desired action, such as "Go to Games" or "Search for Puzzle Games." The system will interpret your commands and take you to the appropriate section of the website. For best results, speak clearly and use simple phrases. If you encounter any issues, refer to the help section for troubleshooting tips.');

-- -- populating blogs table
INSERT INTO blog (BlogTitle, LinkToBlog, BlogDescription, ReadTime)
VALUES ('Blog title placeholder','linkPlaceholder', 'This is a placeholder description for the blog. The actual description will provide a brief overview of the blog content to entice readers to click and read more.', 5);
INSERT INTO blog (BlogTitle, LinkToBlog, BlogDescription, ReadTime)
VALUES ('Blog title placeholder 2','linkPlaceholder', 'This is a placeholder description for the blog. The actual description will provide a brief overview of the blog content to entice readers to click and read more.', 5);
INSERT INTO blog (BlogTitle, LinkToBlog, BlogDescription, ReadTime)
VALUES ('Blog title placeholder 3','linkPlaceholder', 'This is a placeholder description for the blog. The actual description will provide a brief overview of the blog content to entice readers to click and read more.', 5);





