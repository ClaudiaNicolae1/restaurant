 Restaurant 
În această aplicație am realizat un RestAPI cu toate operatiile CRUD necesare pentru
administrarea unui restaurant structurat după cum va fi prezentat în continuare.
Entități salvate în PostgresSQL (fiecare având primary key-ul creat cu Identity):
    Category (relație One to Many cu entitatea MenuItem)
    Customer
    Employee (are rolul sub formă de Enum)
    MenuItem
    Order (relație de Many to Many cu tabela ManuItem prin intermediul entității
    OrderDetail și cu statusul sub formă de Enum)
    OrderDetail
Pentru fiecare entitate avem creată o clasă service ce se va avea rolul de intermediar
între partea de business a aplicației și partea de acces la date. Singura excepție o
fac entitățile Order și OrderDetail ce au aceeași clasă cu rolul Service.
Pentru a extinde informațiile din anumite entități am creat DTO-uri pe care le-am
mapat folosind clasa ModelMapper. Aceste clase sunt urmatoarele:
    MenuItemDTO: mai adăugăm numele categoriei
    OrderDetailDTO: mai adăugăm numele categoriei, prețul produsul din meniu și
    data comenzii
    OrderDTO: mai adăugăm prețul total al comenzii și numele clientului
În cazul unei excepții aruncate de o metodă folosită în servicii, aceasta a fost
prinsă în cadrul controller-ului și returnată către client drept un HTTP response cu
statusul BAD_REQUEST și cu textul excepției în body. Pentru request-urile normale se
returnează ResponseEntity.ok().build().
Partea de Repository a fost creată exntinzând clasa JpaRepository pentru fiecare
entitate, iar pentru OrderDetail a fost adăugată și o metodă custom.
În cadrul testării au fost verificate toate metodele din fiecare controller, iar
pentru fiecare dintre acestea au fost verificate toate cazurile posibile. În total
avem 41 de teste care se află în pachetul test.java.com.restaurant.controllers.
