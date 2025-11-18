-- -- populating the users table
INSERT INTO users (Username, Email, EncryptedPassword)
VALUES ('admin', 'tempEmail', 'tempPassword');
INSERT INTO users (Username, Email, EncryptedPassword)
VALUES ('user1', 'tempEmail1', 'tempPassword1');
INSERT INTO users (Username, Email, EncryptedPassword)
VALUES ('user2', 'tempEmail2', 'tempPassword2');
INSERT INTO users (Username, Email, EncryptedPassword)
VALUES ('user3', 'tempEmail3', 'tempPassword3');

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
VALUES ('Minecraft', 'Mojang Studios', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en_GB', 'https://play-lh.googleusercontent.com/gfNz1N2GNi5piz24IB08RQ4ZGfUnN_kOH8Edhh7uCiotI2P7IBWBXdHzR8gC01ppNnU=w720-h405-rw', 'Game Description', '2011-08-16', '1.21.121', 'Visual support incudes features such as text-to-speech menus and an adjustable UI using mod packs.', 'Audio support includes features such as subtitles and visual sound indicators.', 'Cognitive support features include a creative gameplay that removes pressure.', 'No speech is required.', 'Motor support includes customizable controls and compatibility with assistive devices.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Monument Valley', 'ustwo games', 'PEGI 3', 'https://play.google.com/store/apps/details?id=com.ustwo.monumentvalley&hl=en_GB', 'https://cdn1.epicgames.com/spt-assets/e56a7411805046d3b5b7253a6e4e0faa/monument-valley-1e2yy.jpg', 'Game Description', '2014-04-03', 'no version number available', 'There are no features to alter the UI.', 'Music is atmospheric and not necessary to play the game.', 'Relaxed pace and it is easy to undo and retry.', 'No speech is required.', 'There are simple tap/drag controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Among Us', 'Innersloth LLC', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.innersloth.spacemafia&hl=en_GB', 'https://i.ytimg.com/vi/0YKjFoGxbec/maxresdefault.jpg', 'Game Description', '2018-06-15', '17.0.1', 'Shapes and name tags are used in tasks to help those with colour blindness, however there is limited visual support for those with low/no vision.', 'Minimal/No reliance on audio cues.', 'Mechanics are simple however social deception adds complexity. ', 'No speech is required as all communication is via a messaging system.', 'There are simple tap/drag controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Candy Crush Saga', 'King', 'PEGI 3', 'https://play.google.com/store/apps/details?id=com.king.candycrushsaga&hl=en_GB', 'https://store-images.s-microsoft.com/image/apps.52397.13510798882606697.1816f804-e7fd-4295-9275-23dec3563baf.2ef7ec2d-2e37-489e-b6ac-b5f5e44d429c?q=90&w=480&h=270', 'Game Description', '2012-04-12', '1.315.0.1', 'Visual Support includes a colour blind mode however there is limited visual support for those with low/no vision. ', 'No reliance on audio cues.', 'Repetitive and low pressure gameplay. Some players have reported flashing colours that could affect players with photosensitivity, epilepsy or seizures.', 'No speech is required.', 'Simple swipe controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Call Of Duty®: Mobile', 'Activision Publishing, Inc.', 'PEGI 16', 'https://play.google.com/store/apps/details?id=com.activision.callofduty.shooter&hl=en_GB', 'https://www.gamesatlas.com/images/jch-optimize/ng/images_call-of-duty-mobile_cod-mobile-cover.webp', 'Game Description', '2019-10-01', '1.0.52', 'Visual support include features such as a High Contrast Mode.', 'Audio support includes features such as subtitles and directional sound indicators.', 'Cognative support includes features such as tutorials however there is limited support for those with additional cognitive support. There are also photosensitivity/epilepsy/seizure warnings for this game.', 'Voice chat for this game is optional.', 'Motor support includes customizable controls and compatibility with assistive devices.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Pokemon Go', 'Niantic, Inc.', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.nianticlabs.pokemongo&hl=en_GB', 'https://assets.nintendo.eu/image/upload/f_auto,q_auto/v1624945924/NAL/Mobile/Pok%C3%A9mon%20GO/H2x1_SmartDevice_PokemonGo_London_enGB_image1600w.jpg', 'Game Description', '2016-07-06', '0.385.2', 'There are no features to alter the UI.', 'No reliance on audio cues.', 'Gameplay is repetitive and routine friendly. Some players have reported flashing lights that could affect players with photosensitivity, epilepsy or seizures.', 'No speech is required.', 'Simple tap/drag controls to navigate the game, however some players may struggle to aim when trying to catch some pokemon due to distance and movement. Some features require walking certain distances.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Subway Surfers', 'SYBO Games', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.kiloo.subwaysurf&hl=en_GB', 'https://calendar.cx/wp-content/uploads/2025/05/subwaysurfers.png', 'Game Description', '2012-05-024', '3.54.0', 'There is limited visual support for this game.', 'No reliance on audio cues.', 'Simple repetitive game play', 'No speech is required.', 'Simple swipe controls to navigate the game that allows for one handed play, however some players may struggle with the speed of the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Clash of Clans', 'Supercell', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.supercell.clashofclans&hl=en_GB', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQkdYZR9hESsejBEtjUeWUbKr3FsUmjwz6BBw&s', 'Game Description', '2012-08-02', '17.477.15', 'There is limited visual support for this game.', 'No reliance on audio cues.', 'Gameplay is simple and turn-based.', 'No speech is required.', 'Simple tap controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Jigsawscapes® - Jigsaw Puzzles', 'Oakever Games', 'PEGI 3', 'https://play.google.com/store/apps/details?id=jigsaw.puzzle.game.banana&hl=en_GB', 'https://m.media-amazon.com/images/I/A1bxq4y1RXL.png', 'Game Description', '2023-04-26', '4.2.2', 'Visual support include features such as adjustable contrast/background, however there is limited visual support for players with low/no vision.', 'No reliance on audio cues.', 'Gameplay is simple, relaxing and not timed.', 'No speech is required.', 'Simple tap/drag controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('My Oasis: Relaxing, Satisfying', 'JUNEiNTER Co., Ltd.', 'PEGI 3', 'https://play.google.com/store/apps/details?id=com.buffstudio.myoasis&hl=en_GB', 'https://cdn.aptoide.com/imgs/6/f/2/6f234be40d6930c031f8bcc99275416c_fgraphic.png', 'Game Description', '2025-10-28', 'no version number available', 'There are no features to alter the UI.', 'No reliance on audio cues.', 'Gameplay is simple, calming and non-challenging.', 'No speech is required.', 'Simple tap controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Altos Odyssey', 'Noodlecake', 'PEGI 3', 'https://play.google.com/store/apps/details?id=com.noodlecake.altosodyssey&hl=en_GB', 'https://i.ytimg.com/vi/GcB7c1eHb6M/hqdefault.jpg', 'Game Description', '2018-02-021', '1.3.0', 'There is limited visual support for this game', 'No reliance on audio cues.', 'Gameplay is simple with a lack of complex rules or time limits.', 'No speech is required.', 'Simple tap controls to navigate the game, however some players may struggle with the timing required for jumps.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('KAMI 2', 'State of Play', 'PEGI 3', 'https://play.google.com/store/apps/details?id=com.stateofplaygames.kami2&hl=en_GB', 'https://images.squarespace-cdn.com/content/v1/57b5d1f0d482e93404415213/1490788832173-0RY3VJV0MOS89WICFOGB/image-asset.jpeg', 'Game Description', '2017-03-30', '1.9.3', 'The UI has highly contrasting colours however there is limited visual support for players with low/no vision.', 'No reliance on audio cues.', 'Puzzles are slow and untimed.', 'No speech is required.', 'Simple tap controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Sky: Children of the Light', 'thatgamecompany inc', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.tgc.sky.android&hl=en_GB', 'https://adventuregamers.com/wp-content/uploads/images/screenshots/50122/-header.jpg', 'Game Description', '2019-07-18', '0.31.2', 'There are no features to alter the UI.', 'There are some audio cues however the game is playable with no audio', 'Some players have reported flashing lights that could affect players with photosensitivity, epilepsy or seizures.', 'No speech is required as emotes are used for communication.', 'Simple movement/flight controls to navigate the game, however some players may struggle with the precision required for certain actions.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Stardew Valley', 'ConcernedApe', 'PEGI 12', 'https://play.google.com/store/apps/details?id=com.chucklefish.stardewvalley&hl=en_GB', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRzag19Vq5eT2IZPNZZYU-PxjM1xLc6mEJEnQ&s', 'Game Description', '2019-03-14', '1.6.9', 'Limited visual support is available for the app version of this game.', 'No reliance on audio cues.', 'Gameplay is non-linear and self-paced.', 'No speech is required.', 'Controls are simple and compatible with assistive devices.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('A Blind Legend', 'DOWINO', 'PEGI 3', 'https://a-blind-legend.en.aptoide.com/app', 'https://images.launchbox-app.com/4580cd6a-d441-4745-ac9d-4cc3df4df468.png', 'Game Description', '2015-06-10', '1.2.4', 'Fully accessible, and designed for, players with no/low vision.', 'Fully reliant on audio cues.', 'Gameplay is story structured.', 'No speech is required.', 'Simple tap and swipe controls to navigate the game.');

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
VALUES (4, 'https://cdn-www.bluestacks.com/bs-images/Candy-Crush-Saga_Tips-Tricks_EN_6.jpg', 'Placeholder');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (4, 'https://media-cldnry.s-nbcnews.com/image/upload/t_nbcnews-fp-1200-630,f_auto,q_auto:best/newscms/2014_04/133241/candy-crush-saga-screenshot-01.jpg', 'placeholder');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (4, 'https://i.guim.co.uk/img/media/cce6a7148687a60c19c60f35e1d0dde4fb755775/0_0_1749_1050/master/1749.jpg?width=465&dpr=1&s=none&crop=none', 'placeholder');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (4, 'https://joywikia-ccs.weebly.com/uploads/2/1/2/5/21259908/1163467_orig.png', 'placeholder');
-- call of duty mobile images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (5, 'https://images.squarespace-cdn.com/content/v1/58af450eb3db2b0582612f1d/1574235600878-89O2J69L16OZAKSH60DO/Screenshot+2019-11-20+at+9.39.42.png', 'placeholder');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (5, 'https://static0.thegamerimages.com/wordpress/wp-content/uploads/2019/09/COD-Mobile.jpg?q=50&fit=crop&w=825&dpr=1.5', 'placeholder');
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
VALUES (7, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fmarksangryreview.com%2Fsubway-surfers-guide%2F&psig=AOvVaw0CV7Fm0llw_dLlIZ2F7-wO&ust=1762975282259000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCLiBle7o6pADFQAAAAAdAAAAABAE', 'Colorful game scene of a character running on train tracks, collecting coins. A red tunnel and greenery are in the background, with an approaching train.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (7, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.revid.ai%2Ftools%2Fsubway-surfers-video-generator&psig=AOvVaw0CV7Fm0llw_dLlIZ2F7-wO&ust=1762975282259000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCLiBle7o6pADFQAAAAAdAAAAABAS', 'A skateboarder rides along train tracks in a spooky, Halloween-themed village. Glowing pumpkins line the path, and houses have lit windows.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (7, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fsubway-surfers.fileion.com%2F&psig=AOvVaw0CV7Fm0llw_dLlIZ2F7-wO&ust=1762975282259000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCLiBle7o6pADFQAAAAAdAAAAABAZ', 'A video game scene shows a character named Jake snowboarding along train tracks in a festive city, with snow falling and bright lights glowing.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (7, 'https://chromewebstore.google.com/detail/subway-surfers-offline/khollmcaijfbchfecahodpcmbeajkjgd', 'A character runs along train tracks between two moving trains in a vibrant, colorful cityscape. A police officer and dog chase behind. Energetic, action-packed scene.');
-- clash of clans images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (8, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.reddit.com%2Fr%2FClashOfClans%2Fcomments%2Fyhb6b8%2Finsane_coc_gameplay%2F&psig=AOvVaw0KuWDGiUvehXeHohTQOxZ5&ust=1762975521276000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCLC16OHp6pADFQAAAAAdAAAAABAE', 'A mobile game interface showing a player''s village layout. Six cannons lined up on grass, countdown timers above each. Resource icons and menu options visible.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (8, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.deconstructoroffun.com%2Fblog%2F%2F2012%2F09%2Fclash-of-clans-winning-formula.html&psig=AOvVaw0KuWDGiUvehXeHohTQOxZ5&ust=1762975521276000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCLC16OHp6pADFQAAAAAdAAAAABAL', 'A vibrant village scene from a mobile strategy game showing small buildings, cannons, resources, and character icons on a grassy grid layout.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (8, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.easyfun.gg%2Fcloud-games%2Fclash-of-clans.html&psig=AOvVaw0KuWDGiUvehXeHohTQOxZ5&ust=1762975521276000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCLC16OHp6pADFQAAAAAdAAAAABAS', 'A vibrant battle scene in a mobile strategy game shows troops assaulting a fortress. Bright visuals, stats at the top, and a 50% damage indicator convey victory.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (8, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.docs.balancy.dev%2Fgame_templates%2Fclash_of_clans%2F&psig=AOvVaw0KuWDGiUvehXeHohTQOxZ5&ust=1762975521276000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCLC16OHp6pADFQAAAAAdAAAAABAZ', 'A strategic "Clash of Clans" village layout with defensive structures and resources; includes purple elixir storages and a fortified town hall.');
-- jigsawscapes images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (9, 'https://play.google.com/store/apps/details/Jigsawscapes+-+Jigsaw+Puzzles?id=jigsaw.puzzle.game.banana&hl=en_CA', 'A colorful puzzle depicting a rooster and hen with chicks in a vibrant farm scene. Sunflowers, blue pots, and a scenic river are visible. Title reads ''Puzzle Game for Adults.'' Some pieces are missing, adding an interactive feel.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (9, 'https://play.google.com/pc-store/games/details?id=jigsaw.puzzle.game.banana', 'Jigsaw puzzle of a serene landscape with a windmill by a river, red and pink tulips in the foreground, blue sky. Text reads ''Immersive Experiences.');
-- my oasis images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (10, 'https://play.google.com/store/apps/details?id=com.buffstudio.myoasis', 'Low-poly scenic landscape with pink trees, a pond with lily pads, a panda, and floating island under sun rays. Text reads My Oasis.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (10, 'https://play.google.com/store/apps/details?id=com.buffstudio.myoasis', 'A serene, colorful landscape features a lush green island with a central waterfall, surrounded by diverse trees and soft rolling hills. A bright rainbow arcs across the clear blue sky. The screen displays playful icons and musical notes, suggesting an interactive, game-like environment. The mood is peaceful and whimsical.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (10, 'https://play.google.com/store/apps/details?id=com.buffstudio.myoasis', 'A stylized, low-poly desert oasis with animals by a pond, surrounded by cacti and palm trees. Hot air balloons and a whale in the colorful sky.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (10, 'https://play.google.com/store/apps/details?id=com.buffstudio.myoasis&hl=en_GB', 'Colorful game interface showing animated hearts over a virtual oasis landscape with desert hills, palm trees, and calm water. Icons and notes appear at the bottom.');
-- altos odyssey images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (11, 'https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.hardcoregaming101.net%2Faltos-odyssey%2F&psig=AOvVaw3kZQYhU_b0sefLyHveD8gf&ust=1762976465534000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCICS7q_t6pADFQAAAAAdAAAAABAE', 'Desert landscape at sunset with silhouettes of huts, cacti, and hot air balloons against a glowing sun. Warm, serene atmosphere conveyed.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (11, 'https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.hardcoregaming101.net%2Faltos-odyssey%2F&psig=AOvVaw3kZQYhU_b0sefLyHveD8gf&ust=1762976465534000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCICS7q_t6pADFQAAAAAdAAAAABAL', 'A desert landscape in a video game, with hot air balloons, a setting sun, and distant mountains. A character and lights adorn a cable. Calm and adventurous.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (11, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.imore.com%2Faltos-odyssey-review&psig=AOvVaw3kZQYhU_b0sefLyHveD8gf&ust=1762976465534000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCICS7q_t6pADFQAAAAAdAAAAABAS', 'A silhouetted landscape at sunset with mountains, trees, balloons, and an owl. Game elements include score and distance, creating a serene yet adventurous tone.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (11, 'https://play.google.com/store/apps/details?id=com.noodlecake.altosodyssey', 'Minimalist landscape with a glowing sun, rolling hills, and silhouetted structures. A waterboarder glides, creating splashes. Calm, adventure vibe.');
-- kami 2 images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (12, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.appunwrapper.com%2F2017%2F03%2F31%2Fkami-2-walkthrough-guide-and-solutions%2F&psig=AOvVaw10OEE9bl47ZJjateQEB3lF&ust=1762976182187000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCJCF9Z7s6pADFQAAAAAdAAAAABAE', 'Colorful hexagonal and geometric patterns arranged in three sets of four, each topped by Journey. Each set has a Perfect badge below.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (12, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fnoodlecake.com%2Fgames%2Fkami-2%2F&psig=AOvVaw10OEE9bl47ZJjateQEB3lF&ust=1762976182187000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCJCF9Z7s6pADFQAAAAAdAAAAABAL', 'Colorful geometric pattern with overlapping green and yellow hexagons on a light blue background. The pattern is dynamic and abstract, with a playful tone.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (12, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fnoodlecake.com%2Fgames%2Fkami-2%2F&psig=AOvVaw10OEE9bl47ZJjateQEB3lF&ust=1762976182187000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCJCF9Z7s6pADFQAAAAAdAAAAABAS', 'Abstract geometric pattern with pink, yellow, and white triangular shapes. A small yellow star-like shape is centered on a diagonal pink strip.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (12, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fgames.lol%2Fkami-2%2F&psig=AOvVaw10OEE9bl47ZJjateQEB3lF&ust=1762976182187000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCJCF9Z7s6pADFQAAAAAdAAAAABAZ', 'Geometric puzzle game interface with two abstract designs, one marked "Perfect!" in gold. The scene conveys an achievement or success.');
-- sky children of the light images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (13, 'https://play.google.com/store/apps/details?id=com.tgc.sky.android', 'Four stylized characters with glowing features sit around a bright campfire on a grassy field under a starry night, creating a cozy, magical atmosphere.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (13, 'https://play.google.com/store/apps/details?id=com.tgc.sky.android', 'A figure in a red cape stands on a platform surrounded by clouds, facing a towering, illuminated structure with arches. The scene conveys wonder and adventure.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (13, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fthatgamecompany.helpshift.com%2Fhc%2Fen%2F17-sky-children-of-the-light%2Ffaq%2F484-how-can-i-capture-video-of-the-game%2F&psig=AOvVaw244xCZdlHHGXh7LmXIvmDL&ust=1762976716838000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCJC0jJru6pADFQAAAAAdAAAAABAS', 'A character in a blue cape approaches two mystical portals set in rocky structures, with one glowing blue and the other emitting a pink light. The scene is tranquil.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (13, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fmvcurrent.com%2F9401%2Fopinions%2Fsky-children-of-the-light-is-the-best-game-ever%2F&psig=AOvVaw244xCZdlHHGXh7LmXIvmDL&ust=1762976716838000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCJC0jJru6pADFQAAAAAdAAAAABAZ', 'A character in a flowing cape stands on a grassy hilltop overlooking misty valleys and towering structures, conveying a sense of adventure and serenity.');
-- stardew valley images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (14, 'https://play.google.com/store/apps/details?id=com.chucklefish.stardewvalley&hl=en_GB', 'A pixel-art scene from a farming RPG game shows characters on a wooden dock with a house and purple boat. Overlay reads, The smash hit farming RPG comes to Mobile.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (14, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.indiegamewebsite.com%2F2018%2F10%2F29%2Fstardew-valley-mobile-review%2F&psig=AOvVaw0nEUZ7rSd7Jbt9SunsA5QY&ust=1762976973870000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCPilxpTv6pADFQAAAAAdAAAAABAL', 'Pixel art of a character standing outside a wooden cottage in a farm setting from a game. Grass and a mailbox are visible. The visual has a cozy feel.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (14, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.stardewvalley.net%2Fstardew-valley-coming-to-mobile%2F&psig=AOvVaw0nEUZ7rSd7Jbt9SunsA5QY&ust=1762976973870000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCPilxpTv6pADFQAAAAAdAAAAABAS', 'Pixel art scene from a game: A person in a hat fishes next to a pond in a fall setting, with mushrooms and a cabin nearby. Autumn leaves float in the water.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (14, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Ftasta.me%2Fpros-and-cons-of-stardew-valley-mobile-96020f36a4b1&psig=AOvVaw0nEUZ7rSd7Jbt9SunsA5QY&ust=1762976973870000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCPilxpTv6pADFQAAAAAdAAAAABAg', 'Pixelated farm scene from a video game shows two farmers in hats tending crops near a wooden house, well, and beehives. The setting is bright and cheerful.');
-- a blind legend images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (15, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Frawg.io%2Fgames%2Fa-blind-legend&psig=AOvVaw1yed98RHXORnbe4xwEjcoy&ust=1762977130248000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCKCN1d_v6pADFQAAAAAdAAAAABAL', 'A character with long hair stands on a stone path facing a grand, illuminated building surrounded by trees. The scene conveys an adventurous, mysterious tone.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (15, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fid.techinasia.com%2Fa-blind-legend-game-smartphone-untuk-orang-tunanetra&psig=AOvVaw1yed98RHXORnbe4xwEjcoy&ust=1762977130248000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCKCN1d_v6pADFQAAAAAdAAAAABAf', 'Illustrated instructions on using a smartphone game: 1) Move, 2) Turn, 3) Strike, 4) Block, 5) Repel, 6) Ouch! Gestures guide interactive actions.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (15, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fstore.steampowered.com%2Fapp%2F437530%2FA_Blind_Legend%2F&psig=AOvVaw1yed98RHXORnbe4xwEjcoy&ust=1762977130248000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCKCN1d_v6pADFQAAAAAdAAAAABAn', 'A person wearing headphones and holding a phone appears immersed in music, eyes closed. Mysterious shadowy figures move through a dark, foggy environment. Mood is enigmatic and introspective.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (15, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fkotaku.com%2Fgames%2Fa-blind-legend&psig=AOvVaw1yed98RHXORnbe4xwEjcoy&ust=1762977130248000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCKCN1d_v6pADFQAAAAAdAAAAABAu', 'Two hooded figures stand in a desert landscape with futuristic vehicles and windmills. Flying ships and a colossal artifact loom under a dramatic sky.');

-- -- populating reviews table
INSERT INTO reviews (UserID, GameID, HearingRating, VisualRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (2,1,5,3,4,4,5,'Minecraft offers a fantastic sensory experience with its blocky visuals and immersive sound design. The gameplay is engaging and allows for creativity. The customisable control features allowed me to adapt the game to my needs, making it accessible and enjoyable.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisualRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (2,15, 0,5,4,4,5,'A Blind Legend provides an exceptional auditory experience, making it accessible for visually impaired players. The sound design is immersive, and the gameplay is intuitive. The game effectively combines audio cues with engaging mechanics, creating a unique adventure. The motor controls are manageable, allowing for an enjoyable experience.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisualRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (2, 6, 5,3, 4,3,3,'Pokemon Go offers a fun and interactive experience with its augmented reality features. The sound effects enhance the gameplay, and the visual design is appealing. I struggled a bit with the motor controls, but overall, it was an enjoyable game that encouraged outdoor activity.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisualRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (2, 9,5,3,5,4,4,'Jigsawscapes provides a relaxing and enjoyable puzzle experience. The sound effects are soothing, and the visual design is clear. The gameplay is straightforward, and the motor controls are easy to manage. It''s a great game for unwinding and stimulating the mind.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisualRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (2, 5, 4,2,2,3,3,'Call of Duty Mobile delivers an intense gaming experience with its realistic sound effects and graphics. I found the speed and complexity of the gameplay challenging, particularly with the motor controls. However, the game is engaging and offers a variety of modes to keep players interested.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisualRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (2, 13, 2,2,2,3,2,'Sky Children of the Light offers a visually stunning experience with its beautiful landscapes and calming soundscapes. However , I found the gameplay to be slow-paced and the motor controls somewhat challenging. While the game is visually appealing, it may not provide enough stimulation for all players.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisualRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (3, 15,0,0,2,2, 1,'I could not fully enjoy A Blind Legend due to its heavy reliance on auditory cues, as someone with hearing loss this presented significant challenges.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisualRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (3, 13, 2,5,3,4,3,'The visual design of Sky Children of the Light is stunning, however, the auditory elements were difficult for me to engage with due to my hearing impairment. The gameplay was enjoyable, but I found some of the controls challenging.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisualRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (3, 3, 5,5,4,4,5,'Really fun game! There is no reliance on audio cues, making it accessible for players with hearing impairments.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisualRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (3, 7, 5,4,4,5,5,'No reliance on audio cues, making it accessible for players with hearing impairments. The vibrant visuals and engaging gameplay make Subway Surfers a great choice for all players.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisualRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (4,14, 5,5,5,4,5,'Stardew Valley is an excellent game that offers a rich sensory experience. The gameplay is engaging and allows for creativity. The motor controls are manageable, making it accessible for players, like myself, with motor impairments.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisualRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (4, 3, 5,5,4,3,3,'Gameplay was fun and engaging, but I found some of the motor controls challenging due to my disability. However, the game is still enjoyable and offers a great multiplayer experience, even without audio.');
INSERT INTO reviews (UserID, GameID, HearingRating, VisualRating, CognitiveRating, MotorRating, GameplayRating, ReviewText)
VALUES (4,13,3,5,4,2,3,'I struggled to play this game due to the motor controls and audio cues for dangerous situations. However, the visual design is stunning.');

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
INSERT INTO blogs (BlogTitle, LinkToBlog, BlogDescription, ReadTime)
VALUES ('Blog title placeholder','linkPlaceholder', 'This is a placeholder description for the blog. The actual description will provide a brief overview of the blog content to entice readers to click and read more.', 5);
INSERT INTO blogs (BlogTitle, LinkToBlog, BlogDescription, ReadTime)
VALUES ('Blog title placeholder 2','linkPlaceholder', 'This is a placeholder description for the blog. The actual description will provide a brief overview of the blog content to entice readers to click and read more.', 5);
INSERT INTO blogs (BlogTitle, LinkToBlog, BlogDescription, ReadTime)
VALUES ('Blog title placeholder 3','linkPlaceholder', 'This is a placeholder description for the blog. The actual description will provide a brief overview of the blog content to entice readers to click and read more.', 5);






