USE appdb;

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
VALUES ('Minecraft', 'Mojang Studios', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en_GB', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fminecraft-archive.fandom.com%2Fwiki%2FMinecraft%3A_Bedrock_Edition&psig=AOvVaw36uww1m4P7-f0RmwjdavLL&ust=1762956872536000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCPCTx6Kk6pADFQAAAAAdAAAAABAE', 'Game Description', '2011-08-16', '1.21.121', 'Visual support incudes features such as text-to-speech menus and an adjustable UI using mod packs.', 'Audio support includes features such as subtitles and visual sound indicators.', 'Cognitive support features include a creative gameplay that removes pressure.', 'No speech is required.', 'Motor support includes customizable controls and compatibility with assistive devices.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Monument Valley', 'ustwo games', 'PEGI 3', 'https://play.google.com/store/apps/details?id=com.ustwo.monumentvalley&hl=en_GB', 'https://play.google.com/store/apps/details/Monument_Valley?id=com.ustwo.monumentvalley&hl=en_IE', 'Game Description', '2014-04-03', 'no version number available', 'There are no features to alter the UI.', 'Music is atmospheric and not necessary to play the game.', 'Relaxed pace and it is easy to undo and retry.', 'No speech is required.', 'There are simple tap/drag controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Among Us', 'Innersloth LLC', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.innersloth.spacemafia&hl=en_GB', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Famong-us.fandom.com%2Fwiki%2FRed&psig=AOvVaw3c7CgdM8Z1i8WW79FWchc6&ust=1762957533149000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCLjA-eWm6pADFQAAAAAdAAAAABAE', 'Game Description', '2018-06-15', '17.0.1', 'Shapes and name tags are used in tasks to help those with colour blindness, however there is limited visual support for those with low/no vision.', 'Minimal/No reliance on audio cues.', 'Mechanics are simple however social deception adds complexity. ', 'No speech is required as all communication is via a messaging system.', 'There are simple tap/drag controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Candy Crush Saga', 'King', 'PEGI 3', 'https://play.google.com/store/apps/details?id=com.king.candycrushsaga&hl=en_GB', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fcandy-crush-saga.en.uptodown.com%2Fandroid&psig=AOvVaw0W_ns_AZyNI314JZtI2VaG&ust=1762957921105000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCOCBs5ao6pADFQAAAAAdAAAAABAE', 'Game Description', '2012-04-12', '1.315.0.1', 'Visual Support includes a colour blind mode however there is limited visual support for those with low/no vision. ', 'No reliance on audio cues.', 'Repetitive and low pressure gameplay. Some players have reported flashing colours that could affect players with photosensitivity, epilepsy or seizures.', 'No speech is required.', 'Simple swipe controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Call Of Duty®: Mobile', 'Activision Publishing, Inc.', 'PEGI 16', 'https://play.google.com/store/apps/details?id=com.activision.callofduty.shooter&hl=en_GB', 'https://play.google.com/store/apps/details?id=com.activision.callofduty.shooter', 'Game Description', '2019-10-01', '1.0.52', 'Visual support include features such as a High Contrast Mode.', 'Audio support includes features such as subtitles and directional sound indicators.', 'Cognative support includes features such as tutorials however there is limited support for those with additional cognitive support. There are also photosensitivity/epilepsy/seizure warnings for this game.', 'Voice chat for this game is optional.', 'Motor support includes customizable controls and compatibility with assistive devices.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Pokemon Go', 'Niantic, Inc.', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.nianticlabs.pokemongo&hl=en_GB', 'https://play.google.com/store/apps/details?id=com.nianticlabs.pokemongo&hl=en_GB', 'Game Description', '2016-07-06', '0.385.2', 'There are no features to alter the UI.', 'No reliance on audio cues.', 'Gameplay is repetitive and routine friendly. Some players have reported flashing lights that could affect players with photosensitivity, epilepsy or seizures.', 'No speech is required.', 'Simple tap/drag controls to navigate the game, however some players may struggle to aim when trying to catch some pokemon due to distance and movement. Some features require walking certain distances.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Subway Surfers', 'SYBO Games', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.kiloo.subwaysurf&hl=en_GB', 'https://play.google.com/store/apps/details?id=com.kiloo.subwaysurf&hl=en_GB', 'Game Description', '2012-05-024', '3.54.0', 'There is limited visual support for this game.', 'No reliance on audio cues.', 'Simple repetitive game play', 'No speech is required.', 'Simple swipe controls to navigate the game that allows for one handed play, however some players may struggle with the speed of the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Clash of Clans', 'Supercell', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.supercell.clashofclans&hl=en_GB', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Flogos.fandom.com%2Fwiki%2FClash_of_Clans&psig=AOvVaw2EXjMC281IoLNtdiluoap3&ust=1762958702927000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCIjcwZSr6pADFQAAAAAdAAAAABAE', 'Game Description', '2012-08-02', '17.477.15', 'There is limited visual support for this game.', 'No reliance on audio cues.', 'Gameplay is simple and turn-based.', 'No speech is required.', 'Simple tap controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Jigsawscapes® - Jigsaw Puzzles', 'Oakever Games', 'PEGI 3', 'https://play.google.com/store/apps/details?id=jigsaw.puzzle.game.banana&hl=en_GB', 'https://play.google.com/store/apps/details?id=jigsaw.puzzle.game.banana', 'Game Description', '2023-04-26', '4.2.2', 'Visual support include features such as adjustable contrast/background, however there is limited visual support for players with low/no vision.', 'No reliance on audio cues.', 'Gameplay is simple, relaxing and not timed.', 'No speech is required.', 'Simple tap/drag controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('My Oasis: Relaxing, Satisfying', 'JUNEiNTER Co., Ltd.', 'PEGI 3', 'https://play.google.com/store/apps/details?id=com.buffstudio.myoasis&hl=en_GB', 'https://play.google.com/store/apps/details?id=com.buffstudio.myoasis&hl=en_GB', 'Game Description', '2025-10-28', 'no version number available', 'There are no features to alter the UI.', 'No reliance on audio cues.', 'Gameplay is simple, calming and non-challenging.', 'No speech is required.', 'Simple tap controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Altos Odyssey', 'Noodlecake', 'PEGI 3', 'https://play.google.com/store/apps/details?id=com.noodlecake.altosodyssey&hl=en_GB', 'https://play.google.com/store/apps/details?id=com.noodlecake.altosodyssey&hl=en_IN', 'Game Description', '2018-02-021', '1.3.0', 'There is limited visual support for this game', 'No reliance on audio cues.', 'Gameplay is simple with a lack of complex rules or time limits.', 'No speech is required.', 'Simple tap controls to navigate the game, however some players may struggle with the timing required for jumps.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('KAMI 2', 'State of Play', 'PEGI 3', 'https://play.google.com/store/apps/details?id=com.stateofplaygames.kami2&hl=en_GB', 'https://play.google.com/store/apps/details?id=com.stateofplaygames.kami2&hl=en_GB', 'Game Description', '2017-03-30', '1.9.3', 'The UI has highly contrasting colours however there is limited visual support for players with low/no vision.', 'No reliance on audio cues.', 'Puzzles are slow and untimed.', 'No speech is required.', 'Simple tap controls to navigate the game.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Sky: Children of the Light', 'thatgamecompany inc', 'PEGI 7', 'https://play.google.com/store/apps/details?id=com.tgc.sky.android&hl=en_GB', 'https://play.google.com/store/apps/details?id=com.tgc.sky.android', 'Game Description', '2019-07-18', '0.31.2', 'There are no features to alter the UI.', 'There are some audio cues however the game is playable with no audio', 'Some players have reported flashing lights that could affect players with photosensitivity, epilepsy or seizures.', 'No speech is required as emotes are used for communication.', 'Simple movement/flight controls to navigate the game, however some players may struggle with the precision required for certain actions.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('Stardew Valley', 'ConcernedApe', 'PEGI 12', 'https://play.google.com/store/apps/details?id=com.chucklefish.stardewvalley&hl=en_GB', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.taptap.io%2Fapp%2F33576107&psig=AOvVaw1MSCCU4K2a3PmfmpmPI0PQ&ust=1762971017699000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCPD5mYTZ6pADFQAAAAAdAAAAABAE', 'Game Description', '2019-03-14', '1.6.9', 'Limited visual support is available for the app version of this game.', 'No reliance on audio cues.', 'Gameplay is non-linear and self-paced.', 'No speech is required.', 'Controls are simple and compatible with assistive devices.');
INSERT INTO games (GameName, Developer, AgeRating, LinkToStore, GameIconURL, GameDescription, ReleaseDate, VersionNumber, AboutVision, AboutHearing, AboutCognitive, AboutSpeech, AboutMotor)
VALUES ('A Blind Legend', 'DOWINO', 'PEGI 3', 'https://a-blind-legend.en.aptoide.com/app', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fapps.apple.com%2Fau%2Fapp%2Fa-blind-legend%2Fid973483154&psig=AOvVaw356e5L34O2xhigR0x7TShW&ust=1762971471983000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCJjCxNTa6pADFQAAAAAdAAAAABAE', 'Game Description', '2015-06-10', '1.2.4', 'Fully accessible, and designed for, players with no/low vision.', 'Fully reliant on audio cues.', 'Gameplay is story structured.', 'No speech is required.', 'Simple tap and swipe controls to navigate the game.');

-- -- population gallery table
DELETE FROM gallery;
ALTER TABLE gallery AUTO_INCREMENT = 1;

-- minecraft images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (1,'https://www.minecraft.net/content/dam/minecraftnet/games/badger/key-art/Homepage_Discover-our-games_MC-Legends-KeyArt_864x864.jpg','First-person view in Minecraft showing a grassy hillside made of blocky dirt and grass layers under a clear blue sky. The player’s hand and hotbar are visible at the bottom of the screen, indicating survival mode with full health and hunger bars.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (1, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fminecraft.wiki%2Fw%2FGameplay&psig=AOvVaw3ebpbXv_A-HyuNgZyoKECw&ust=1762971879251000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCLDF6KXc6pADFQAAAAAdAAAAABAK', 'First-person view in Minecraft showing a player standing beside a river bordered by grassy banks and dense trees, including oak and birch. The sky is bright with scattered clouds, and the player’s survival mode interface with full health and hunger bars is visible at the bottom.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (1, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fterracoders.com%2Ftaxonomy%2Fterm%2F178&psig=AOvVaw3ebpbXv_A-HyuNgZyoKECw&ust=1762971879251000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCLDF6KXc6pADFQAAAAAdAAAAABAR', 'First-person view in Minecraft showing a player holding a diamond sword and shield, looking toward a village built on swampy terrain beside a mountain. The scene includes wooden and stone buildings, one with purple blocks and another tall stone tower connected by a long bridge. The player’s survival interface shows health, hunger, and experience levels.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (1, 'https://www.google.com/imgres?q=minecraft%20with%20subtitles&imgurl=https%3A%2F%2Fedusupport.minecraft.net%2Fhc%2Fuser_images%2F9tVBjYqlMS-O3qbKgN5kEQ.png&imgrefurl=https%3A%2F%2Fedusupport.minecraft.net%2Fhc%2Fen-us%2Fcommunity%2Fposts%2F4403295373972-Subtitles&docid=041L9Cd2fyfBKM&tbnid=wHG3Xt2sI82ITM&vet=12ahUKEwix-OLG3-qQAxUzUkEAHfhvGl4QM3oECBwQAA..i&w=1920&h=1039&hcb=2&ved=2ahUKEwix-OLG3-qQAxUzUkEAHfhvGl4QM3oECBwQAA', 'Minecraft first-person view showing a player standing on a wooden dock in front of a house with glass windows and a wooden door. The player holds a shield and enchanted pickaxe, and game subtitles like Pressure Plate clicks and Door creaks appear on the screen.');
-- monument valley images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (2, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.monumentvalleygame.com%2F&psig=AOvVaw15jcWRDg7wiixJ86mp8Lb0&ust=1762973067161000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCIDYxc7g6pADFQAAAAAdAAAAABAE', 'A surreal, pastel-colored 3D puzzle scene featuring geometric architecture floating above calm water. A small figure in purple stands on an impossible structure leading to a tower with a red flag, illuminated by soft pink and purple light.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (2, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fthenvm.org%2Fgames%2Fmonument-valley%2F&psig=AOvVaw15jcWRDg7wiixJ86mp8Lb0&ust=1762973067161000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCIDYxc7g6pADFQAAAAAdAAAAABAS', 'A dreamy, pastel-colored scene of floating architectural structures with arches, stairs, and platforms rising above the clouds. A small white-robed figure and a dark crow-like figure stand on separate paths within the surreal, Escher-inspired landscape.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (2, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fnews.xbox.com%2Fen-us%2F2025%2F07%2F18%2Fhow-monument-valley-3-uses-soundscapes-to-tell-its-story%2F&psig=AOvVaw15jcWRDg7wiixJ86mp8Lb0&ust=1762973067161000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCIDYxc7g6pADFQAAAAAdAAAAABAZ', 'A vibrant, dreamlike scene showing a golden temple-like structure with arches, towers, and orange trees surrounded by calm turquoise water. Small characters explore the architecture, and a large shadow of a whale can be seen beneath the surface.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (2, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fgamingtrend.com%2Freviews%2Fmonument-valley-panoramic-edition-review-pretty-perspective-puzzles%2F&psig=AOvVaw15jcWRDg7wiixJ86mp8Lb0&ust=1762973067161000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCIDYxc7g6pADFQAAAAAdAAAAABAg', 'A serene, surreal scene featuring two small figures standing on purple geometric architecture suspended in a pink and violet sky. The structures have arches, platforms, and buttons, evoking an abstract puzzle-like environment.');
-- among us images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (3, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.innersloth.com%2Fgames%2Famong-us%2F&psig=AOvVaw3i-cOXIxjTE-HZPdFFBv5p&ust=1762973459028000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCOC7ioji6pADFQAAAAAdAAAAABAE', 'Six colorful characters in spacesuits gather in an electrical room in the game Among Us. Wires and tasks are visible. A green task bar at the top shows progress. The atmosphere is tense and strategic.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (3, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.innersloth.com%2Fgames%2Famong-us%2F&psig=AOvVaw3i-cOXIxjTE-HZPdFFBv5p&ust=1762973459028000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCOC7ioji6pADFQAAAAAdAAAAABAK', 'Game lobby of "Among Us" showing 12 colorful characters, each labeled with a color, beside floating computers. Game settings are listed on the left.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (3, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.ign.com%2Fwikis%2Famong-us%2FHow_To_Play&psig=AOvVaw3i-cOXIxjTE-HZPdFFBv5p&ust=1762973459028000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCOC7ioji6pADFQAAAAAdAAAAABAR', 'A red character stands in the center of a sci-fi themed game environment labeled Storage. A task bar at the top shows progress. The atmosphere feels strategic and mysterious.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (3, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.esports.net%2Fnews%2Fmobile-games%2Fis-among-us-free%2F&psig=AOvVaw3i-cOXIxjTE-HZPdFFBv5p&ust=1762973459028000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCOC7ioji6pADFQAAAAAdAAAAABAl', 'Four colorful astronaut-like characters gather around a round table in a virtual cafeteria setting from the game "Among Us." The floor is gray with shadows.');
-- candy crush saga images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (4, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fpin%2Fcandy-crush-saga-iphone-gameplay-19-youtube--630926229032214580%2F&psig=AOvVaw2U76qOflQa-1ei2sGLbK2K&ust=1762974456409000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCOig3-fl6pADFQAAAAAdAAAAABAS', 'A colorful candy-matching game grid with various candies in blue, green, red, orange, and purple. Scoreboard shows a high score and target. A whimsical cartoon character is visible with a playful expression.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (4, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.mobygames.com%2Fgame%2F61070%2Fcandy-crush-saga%2Fscreenshots%2F&psig=AOvVaw2U76qOflQa-1ei2sGLbK2K&ust=1762974456409000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCOig3-fl6pADFQAAAAAdAAAAABAZ', 'A colorful Candy Crush game board filled with various candies in rows. The left side displays a target score of 60000, with 50 moves and a score of 0.');
INSERT INTO gallery (GameID, ImageURL, AltText)
-- call of duty mobile images
VALUES (5, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.indiatoday.in%2Ftechnology%2Ffeatures%2Fstory%2Fcall-of-duty-mobile-launches-tomorrow-new-gameplay-modes-better-graphics-and-why-pubg-players-should-try-it-1604723-2019-09-30&psig=AOvVaw16SkNR8-RtOKl8vmYq9V9O&ust=1762974813178000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCODJ7Y7n6pADFQAAAAAdAAAAABAE', 'A first-person shooter game scene showing soldiers in camo gear aiming rifles. Tanks and a warehouse are in the background. On-screen controls visible.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (5, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.deconstructoroffun.com%2Fblog%2F2019%2F11%2F20%2Fhow-call-of-duty-mobile-left-100m-on-the-table&psig=AOvVaw16SkNR8-RtOKl8vmYq9V9O&ust=1762974813178000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCODJ7Y7n6pADFQAAAAAdAAAAABAL', 'First-person shooter game scene showing an urban area with a player holding a gun. Vehicles and houses line the street. The interface displays a map and ammo count.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (5, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.videogameschronicle.com%2Fnews%2Fcall-of-duty-mobile-playable-on-pc%2F&psig=AOvVaw16SkNR8-RtOKl8vmYq9V9O&ust=1762974813178000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCODJ7Y7n6pADFQAAAAAdAAAAABAS', 'Intense battle scene from Call of Duty Mobile. Soldiers engage in a firefight near a yellow house, with a drone overhead and explosions in the background, creating a dynamic and action-packed atmosphere.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (5, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.thegamer.com%2Fcall-of-duty-mobile-guide%2F&psig=AOvVaw16SkNR8-RtOKl8vmYq9V9O&ust=1762974813178000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCODJ7Y7n6pADFQAAAAAdAAAAABAZ', 'Two armed soldiers engage in battle near a Mediterranean-style building with a large explosion.');
-- pokemon go images
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (6, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Ftechcrunch.com%2F2017%2F12%2F06%2Fpokemon-go-weather%2F&psig=AOvVaw33z4DCnfS0qZmzIQSxOOIq&ust=1762975099900000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCNjzsZbo6pADFQAAAAAdAAAAABAE', 'Screenshot from a mobile game showing changing weather effects: sunny, cloudy, and rainy settings with a character in a digital map environment.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (6, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Feegamesmart.co.uk%2Fgameguides%2Fpokemon-go%2F&psig=AOvVaw33z4DCnfS0qZmzIQSxOOIq&ust=1762975099900000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCNjzsZbo6pADFQAAAAAdAAAAABAK', 'A collage of four Pokémon GO gameplay images. Players encounter various Pokémon: Doduo, Metapod, Smeargle, and Sandshrew, in vibrant outdoor settings.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (6, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fuk.pcmag.com%2Fmobile-games%2F82981%2Fpokemon-go-for-iphone&psig=AOvVaw33z4DCnfS0qZmzIQSxOOIq&ust=1762975099900000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCNjzsZbo6pADFQAAAAAdAAAAABAR', 'A Pokémon Go game screen shows an avatar surrounded by virtual creatures on a map. PokéStops are visible, creating a lively and adventurous atmosphere.');
INSERT INTO gallery (GameID, ImageURL, AltText)
VALUES (6, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.uxpin.com%2Fstudio%2Fblog%2Fbeyond-hype-ux-reality-check-pokemon-go%2F&psig=AOvVaw33z4DCnfS0qZmzIQSxOOIq&ust=1762975099900000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCNjzsZbo6pADFQAAAAAdAAAAABAY', 'Split-screen image showing Pokémon GO gameplay. Left: Growlithe on a sidewalk. Center: Voltorb on grass. Right: Nidoran♂ near a street. Bright, sunny day.');
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
INSERT INTO blog (BlogTitle, LinkToBlog, BlogDescription, ReadTime)
VALUES ('Blog title placeholder','linkPlaceholder', 'This is a placeholder description for the blog. The actual description will provide a brief overview of the blog content to entice readers to click and read more.', 5);
INSERT INTO blog (BlogTitle, LinkToBlog, BlogDescription, ReadTime)
VALUES ('Blog title placeholder 2','linkPlaceholder', 'This is a placeholder description for the blog. The actual description will provide a brief overview of the blog content to entice readers to click and read more.', 5);
INSERT INTO blog (BlogTitle, LinkToBlog, BlogDescription, ReadTime)
VALUES ('Blog title placeholder 3','linkPlaceholder', 'This is a placeholder description for the blog. The actual description will provide a brief overview of the blog content to entice readers to click and read more.', 5);






