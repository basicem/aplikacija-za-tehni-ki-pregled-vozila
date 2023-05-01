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

![image](https://user-images.githubusercontent.com/96090279/235446998-92a65053-7c2e-46fb-9e67-2032fbc4f66e.png)

Da bi se napravila veza izmedju objekata u bazi I onih u aplikaciji, kreirani su modeli klase koje prate
JavaBean specifikaciju.
