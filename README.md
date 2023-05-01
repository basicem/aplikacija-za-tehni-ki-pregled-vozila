# Aplikacija za tehnički pregled vozila

## Opis aplikacije

Aplikacija koja ce biti opisana predstavlja jedan vid apstrakcije obavljanja tehcnikih pregleda. Ogranicenih je
akcija I vrlo jednostavna.
U aplikaciji imamo 2 pogleda, administrator I uposlenik. Uposlenik ima pravo da vidi I vrsi pretragu samo za
svoje obavljenje tenicke preglede, dok administrator ima sva prava pristupa. Moze da vidi sve tehnicke
preglede, dodaje uposlenike na preglede, dodaje nove uposlenike u firmu I brise ih.
Pored toga oba nivoa imaju pravo zakazivanja termina I otkazivanja termina (uposlenici imaju pravo otkazati
samo svoj).
Pored toga, administrator ima pravo da vidi izvjestaje. Moze da vidi sva vozila koja su tehnicki ispravna, vozila
koja nisu ispravna I ima pravo vidjeti broj pregledanih vozila tokom svakog mjeseca svake godine.

## Osnovne ideje pri implementaciji

Izgled aplikacije je jako jednostavan, intuitivan za koristenje.
Kako bi se postigla veza izmedju objekata baze I aplikacije koristeni su modeli klasa sa JavaBean
specifikacijom.
Prilikom imenovanja klasa, metoda, css, fxml file-ova potrudila sam se da imena budu smislena I da se iz
samog imena vidi za sta sta sluzi.
Takodjer kod samog projekta je razvrstan u src(klase) folder I resource folder. Svaka od klasa je razvrstana u
paket u kojem bi logicki trebala biti(npr. svi controlleri su u paketu controllers itd).

## Baza podataka

Koristena je SQLite baza podataka. Koristeno je ukupno 5 tabela. Tabela technical_inspection_team koristena
kao medjutabela izmedju employee I technical_inspection da bismo mogli realizovati da vise uposlenika bude
na jednom projektu I obrnuto. U prilogu se nalazi ERD dijagram baze. Atributi iz tabele vehicle(brand, model,
type..) I technical_inspection(engine_type, engine_tack...) su mogli biti realizovani kao tabele, ali zbog velikog
broja potrebnih tabela odlucila sam se da budu tipa enum. Rad sa bazom se nalazi u DAO klasama.

![image](https://user-images.githubusercontent.com/96090279/235447124-6f1b05fa-5e89-4632-8b8b-f150648e037d.png)

Da bi se napravila veza izmedju objekata u bazi I onih u aplikaciji, kreirani su modeli klase koje prate
JavaBean specifikaciju.

## Koncepti OOP-a

Iskoristeno nasljedjivanje kod DAO klasa. Posto je koristen veliki broj DAO klasa kreirana klasa BaseDAO koja
stvara konekciju na bazu. Klase CustomerDAO, EmployeeDAO, TechnicalInspectionDAO,
TechnicalInspectionTeamDAO, VehicleDAO su nasljedene iz klase BaseDAO.

### Graficki interfejs
Prilikom implementacije koristen je veci broj GUI elemenata. Po nekom pravilu svaka aplikacija bi trebala da
ima glavni meni. Tu je koristen MenuBar zajedno sa MenuItems.
Pored ovoga koristeni su: Button, ListView, TableView(dobar nacin prikazivanja raznih atributa Tehnickog
pregleda), DatePicker, ChoiceBox, RadioButton, CheckBox, Label, TextField, TextArea, TextFlow, GridPane,
PasswordField, PieChart, BorderPane I drugi.
Za izgled mnogih od GUI elemenata koristeni su css fileovi(dizajn dugmadi, ikonice, boje teksta, pozadine...).
Za prikaz validiranja polja pozivamo se na css fileove. Pored toga za pogresan format nekog elementa
koristene su dinamicke poruke(npr. Na prvom prozoru ukoliko username I lozinka ne odgovaraju imamo
poruku, ili kod unosenja registracije postoji format koji mora biti zadovoljen, pa se takodjer prikazuje porukica
ukoliko taj format nije zadovoljen). Za neke bitnije radnje kao brisanje korisnika, zakazivanja termina,
kompletiranja termina koristeni su dijalozi.
U obzir uzeti Gestalt principi, svi logicki vezani elementi grupisani I na ekranu.

## Datoteke
Prilikom pretrazivanja podataka, informacije koje su filtirane mogu se zapisati u .txt file kako bi se arhivirali
trazeni podaci. To imamo u metodi clickSave u SearchController-u.
##Enumi, izuzeci
U odjejljku sa datotekama sam spomenula mnogi broj enuma I razlog koristenja bas enuma a ne kao zasebne
tabele. Enum koristen u vidu odabira marke vozila, tipa vozila, statusa tehnickog pregeda, vrste tehnickog
pregleda. Kreirane klase za izuzetke, pogresan format telefonskog broja, registracije, broj sasije I izuzetak
kada je odabrani termin zauzet.
##Tredovi
Koristen thread u HomeController-u za prikazivanje trenutnog vremena, da bi aplikacija mogla raditi
nesmetano.
## Funkcionalno programiranje I kolekcije
Koristene lambda funkcije, streamovi su koristeni kod filtriranja podataka(filtriranje pregleda po klijentima, tipu
vozila,datumu). Koristen ArrayList I HashMap. HashMapa je koristena za potrebe kreiranja liste modela vozila
po marki vozila.

## Izvjestavanje
Izvjestaji su implementirani koristenjem JasperStudio alata. U aplikaciji imamo dugme Reports cijim klikom
setiramo prozor za izvjestaje. Imamo tri izvjestaja, lista svih validnih vozila, lista svih vozila koja nisu validna I
broj pregledanih vozila tokom svakog mjeseca.
## Lokalizacija
U resource se nalaze bundle-i koji omogucavaju realizaciju aplikacije na dva jezika. Kao defaulf jezik je
Engleski ali u MenuBar imamo MenuItem Language gdje imamo mogucnost mijenjanja jezika. Pored
Engleskog jezika imamo Bosanski jezik.

## Maven
Uvedene su sqlite-jdbc, jasperreports I testfx-junit. 

![image](https://user-images.githubusercontent.com/96090279/235447373-8c61e4f5-64d1-4e39-82a3-e35d71fba363.png)


Admin
username: mehomehic
password: lozinka

Uposlenik
username:lanalanic
password:lozinka


