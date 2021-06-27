use app;
select * from bestellungen b inner join plaetze p on b.platz_id = p.id where not exists (select * from bestellungen ba inner join plaetze pa on ba.platz_id = pa.id where ba.status_id = 1 and pa.tisch_id = p.tisch_id) and b.status_id = 2;
