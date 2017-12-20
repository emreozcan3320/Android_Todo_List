ToDo List Application Assigment

Note : Ayrıntılı açıklamalar yorum satırı olarak koda eklenmiştir.

Main Activity

	Main Activity de 7 adet method bulunmakta bunlar sırasıyla ;

    1. onCreate
    2. onCreateOptionsMenu
    3. onOptionsItemSelected
    4. deleteTask
    5. updateTask
    6. changeDate
    7. updateUI


	onCreate 

		Bu methoda ileride Database den çekiceğim dataları göstermek bir ListView ‘i id ile 	
    bulndu. Ayrıca database bağlanmak için kullanılacak bir mHelper objesi yarattılan 	
    TaskDbHelper class’ından oluşturdu.

	  mHelper = new TaskDbHelper(this);
	  mTaskListView = (ListView) findViewById(R.id.list_todo);

	
	onCreateOptionsMenu 

		Todo eklemek için menuye bir Add Task buttonu eklendi. Menu üzerinde icon 	
    şeklinde görünmesi için android drawable’larından ic_menu_add seçildi. 
    XML olarak 	oluşturulan bu yapı  onCreateOptionsMenu da

 	getMenuInflater().inflate(R.menu.main_menu, menu);

	  Komutu ile tepede ki menuye eklendi

	updateUI 

		Burda database’e bağlanılarak elde edilen datalar ListView’e aktarılmakta. 	
    Database’ e SQLiteDatabase db = mHelper.getReadableDatabase(); komutu 	
    ile ulaşıldıktan sonra bir cursor’a db.query ile elde edilen tata atanır.
		
		taskList adi ile oluşturulan bir String ArrayList’e cursor’dan çekilen data atanır.
	  Sonrasında bu taskList’i listview de göstermek için adapte edilir. 
	
	deleteTask

		Seçilen bir itemi listeden kaldırır. Database’den çekilen data string işlemlerine sokarak 
    item’in title’lını elde ederiz ve  title’a göre delete edilir.

	UpdateTask and changeDate
		
		İkiside aynı işi yapmakta ama biri liste elemanının yani itemin 
    içeriğini diğeri ise tarihini değiştirmekte. 
    İtemin içeriği alınır ve Alertdialog’a default değer olark atanır 
    onrasında elde yeni değer tablodaki eskisi ile değiştirilir.
		
Second Activity

	Secon Activity bir EditText,TextView, Button ve DatePicker’dan oluşmakta. 
  EditText’en hatırlatma girilir. DatePicker’dan ise hatırlatma tarihi eklenir. 
  Tarih kaydedilmeden önce TextView de gösterilir.
	


TaskDbHelper 

	Bu class SQLiteOpenHelper dan extends edilir. SQLite ile Activity’lerde 
  gerçekleştirilecek SQL işlemleri arasında köprü görevi görür.


TaskContract

	Bu class database ismini, ilerde gelicek güncellemeler için versiyon 
  bilgisini, tablo ve colum bilgilerini tutmaktadır.

Database

Auto increment id ve iki tane title ve date adlı text tipinde toplam 3 date rowdan oluşmaktadır. 
Database in ilk sürümüne date row’ u eklenerek ikinci sürüme geçilmiştir.

XML

activity_main ve activity_second olmak üzere ikitane main aktivite layout’u bulunmakatadır. 
activity_main’e çağrılan birde item_todo layout’u bulunmaktadır. 
Ayrıca menu altında menu_main adlı bir menu elemanı için oluşturulmuş xml dosyası daha vardır.
