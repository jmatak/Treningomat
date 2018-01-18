
## 1.	Opis problema
Upravljanje sportskim aktivnostima proces je koji se hijerarhijski proteže od onih koji izdaju svoja sredstva na korištenje do korisnika samih sredstava. Svi su upoznati kako administracija i vođenje određene grupe ljudi zna biti konfuzan posao, pogotovo kada se ona vodi nemarno putem jedne obične bilježnice. Kao prijedlog rješavanja problema prenapučenosti grupa za trening, neplaćanja članarine, straha od gubljenja i krivotvorenja administracije, nudi se jedno aplikacijsko rješenje. Kako za same voditelje treninga, aplikacija bi i korisnicima nudila lakše snalaženje u procesu življenja sportskim životom.


## 2.	Opis funkcionalnosti

Korisnik na početku rada otvara stranicu sa općim informacijama o grupnom treningu: lokaciji, kategorijama dostupnih sportova, broju trenutno prijavljenih grupa sa ispisanim brojem polaznika u određenoj grupi, terminima treninga, podacima o voditeljima. Početna stranica nudi mogućnost jednostavnog ulaska u druge funkcije sustava. Pristup tim funkcijama ograničen je prema različitim ovlastima koje imaju korisnici sustava. Prema ovlastima u sustavu razlikujemo neprijavljene korisnike, prijavljene korisnike polaznike treninga, voditelje treninga i administratore. 

Administrator ima ovlasti dodavanja voditelja treninga i njihovih brisanja kao i uređivanja sportskih aktivnosti koji se nude u sklopu grupnih treninga. Prilikom unošenja voditelja unose se podaci o imenu, prezimenu te opći pristupni podaci (email i zaporka). Voditelji treninga su zaduženi za uređivanje informacija o treningu i upravljanje administracijom treninga. Polaznici treninga kao zahtjev za ulazak u sustav predaju osobne podatke poput imena, prezimena, OIBa , slike identifikacijske iskaznice (osobna iskaznica ili Xica) i općih pristupnih podataka. Zahtjev se prosljeđuje voditeljima odabrane vrste treninga od kojih bilo koji ima mogućnost odobravanja ili odbacivanja prijave, dok se prijave bez važećih podataka ne smatraju ispravnima i odmah su odbijene.

Prilikom unosa pristupnih podataka, korisnici (polaznici) treninga na svojoj stranici vide trenutno formirane grupe za trening. Korisnik je u mogućnosti poslati zahtjev za prebacivanje ili ulazak u grupu ukoliko željena grupa već nije popunjena . Broj polaznika grupe u mogućnosti je promijeniti samo voditelj treninga koji je vlasnik te grupe ali odabirom grupe upravljaju korisnici. 

Odabirom opcije kalendara, prikazuje se raspored treninga formiran za trenutni tjedan. Pri dnu stranice prikazan je opis sljedećeg treninga. Opis treninga sadrži vrijeme početka, dužinu njegovog trajanja, ocjenu intenziteta i kratki opis vježbi. Nakon što je završio, onim korisnicima koji su zabilježeni da su pohađali trening otvara se mogućnost ocjenjivanja treninga. Kalendar uređuju voditelji treninga.

Krajem svakog mjeseca, u sustavu se, svim korisnicima koji su se predbilježili kao polaznici idućeg mjeseca, kao i onima koji pošalju zahtjev, generira nalog za plaćanje mjesečne članarine. Nalog za uplatu mora sadržavati sve nužne podatke za provedbu novčane transakcije te dodatno barkod oznaku koja će omogućiti korisnicima brže i jednostavnije plaćanje. Nalozi za uplatu šalju se u osobni pretinac pojedinog polaznika.

Voditeljima treninga bit će ponuđena stranica za vođenje administracije plaćanja mjesečne naknade za trening, kao i administracija dolaska na treninge. Korisnicima koje je trener zatekao na treningu, a nisu izvršili svoje obaveze plaćanja trener je u mogućnosti poslati opasku o neplaćanju.

Unutar sustava prijavljenim korisnicima bit će vidljivo takozvano „Zajedničko igralište“ na kojem će se nuditi mogućnost da korisnici i treneri ostave komentare kako bi skupina u što boljoj atmosferi dočekala sljedeći trening ili pak izrazila nezadovoljstvo nekim dijelovima treninga. Poseban dio „Zajedničkog igrališta“ su važne obavijesti koje uređuju treneri i služe kako bi se polaznike obavijestilo o nekim nadolazećim promjenama. 

Nakon određenog broja treninga kod određenog trenera korisnici će biti u prilici ostaviti numeričku (1-5) i opisnu ocjenu trenera. 

Kako je sport važna karika u životu mladih ljudi, potencijalna aplikacija
učinila bi mjesto treninga lišeno administracijskih problema i lako dostupno preko web usluge. Takva aplikacija ponudila bi ujedno i kvalitetnu uslugu i onim grupnim treninzima koji su organizirani za mali broj ljudi i nemaju iza sebe veliku popularnost. Kao poticaj na ovakvu ideju primjer su dali treninzi i sportske aktivnosti organizirane od strane Studentskog Centra koje ljudima voditeljima treninga zadaju glavobolje prilikom organizacije i administracije. 

Potencijalni korisnici aplikacije bili bi svi oni koji su uključeni u nekakve sportske aktivnosti. Aplikacija je jednostavno primjenjiva za bilo kakve oblike grupnih treninga nevezanih za njihovu tematiku. S obzirom da su to uglavnom ljudi mlađih generacija web aplikacija je idealno rješenje zbog visoke informacijske pismenosti u tim krugovima ljudi.


## 3.	Ostali zahtjevi 

Aplikacija bi trebala biti izvedena kao web aplikacija prilagođena (engl. responsive) mobilnom uređaju kojoj će korisnici pristupati uz pomoć korisničkog imena i lozinke. Sustav će biti realiziran preko nekog objektno orijentiranog jezika poput Jave ili PHP-a koristeći posebne frameworke kao što su Spring ili Symfony , dok bi oblikovanje stranice bilo izvedeno uporabom HTML, CSS-a i jQuery biblioteke. Prilikom ispitivanja sustava trebaju biti uneseni podaci za barem jednu vrstu treninga, jednog trenera, više polaznika treninga i više formiranih grupa.


###  Lokacija servera

http://18.216.250.7:8080/