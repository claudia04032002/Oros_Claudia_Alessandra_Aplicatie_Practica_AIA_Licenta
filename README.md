# Controlul vocal al unui vehicul autonom de mici dimensiuni

## 1. Descriere Generală

Acest proiect constă într-o aplicație Android ce permite controlul dispozitivelor Bluetooth folosind comenzi vocale. Scopul aplicației este de a recunoaște comenzile vocale rostite de utilizator și de a transmite instrucțiuni către un dispozitiv conectat prin Bluetooth.

---

## 2. Adresa repository-ului

  ** https://github.com/claudia04032002/Oros_Claudia_Alessandra_Aplicatie_Practica_AIA_Licenta **

Repository-ul conține întregul cod sursă al aplicației, fără fișiere binare sau compilate.

---

## 3. Structura livrabilului

- Codul sursă Java: `MainActivity.java`, `myAdapter.java` și alte fișiere sursă din directorul proiectului.
- Resurse: fișiere XML pentru layout și manifestul aplicației.
- **NU** include fișiere binare, APK-uri generate sau alte fișiere compilate.

---

## 4. Pași de compilare a aplicației

1. **Clonarea repository-ului:**
   ```bash
   git clone https://github.com/claudia04032002/Oros_Claudia_Alessandra_Aplicatie_Practica_AIA_Licenta.git
   ```
  

2. **Deschiderea proiectului:**
   - Deschideți Android Studio.
   - Selectați opțiunea „Open an existing project” și alegeți directorul clonat.

3. **Configurarea mediului:**
   - Asigurați-vă că aveți instalată o versiune recentă de Android Studio și SDK-ul pentru Android 6.0 (API 23) sau mai nou.
   - Verificați fișierul `build.gradle` pentru a corespunde cerințelor de compilare.

4. **Compilare:**
   - Apăsați pe „Build” sau `Run` în Android Studio pentru a compila aplicația.
   - Asigurați-vă că nu există erori de compilare și că toate dependențele sunt rezolvate.

---

## 5. Pași de instalare și lansare a aplicației

1. **Instalarea pe dispozitiv:**
   - Conectați un dispozitiv Android cu debugging USB activat sau folosiți un emulator (doar pentru testarea UI).
   - Din Android Studio, selectați dispozitivul și apăsați `Run`.
   - Alternativ, generați un APK (`Build > Build APK(s)`) și instalați-l manual pe dispozitiv.

2. **Permisiuni necesare:**
   - La prima lansare, aplicația va solicita permisiuni pentru: microfon, Bluetooth, locație și vibrații.
   - Acceptați toate permisiunile pentru funcționarea corectă a aplicației.

3. **Lansare și utilizare:**
   - Odată lansată, aplicația va afișa lista dispozitivelor Bluetooth împerecheate.
   - Selectați dispozitivul de controlat.
   - Țineți apăsat pe butonul microfon pentru a rosti o comandă vocală.
   - Comanda este recunoscută și transmisă către dispozitivul Bluetooth selectat.

---

## 6. Observații

- Pentru funcționare corectă, asigurați-vă că dispozitivul Android are Bluetooth activ și este împerecheat cu dispozitivul ce va fi controlat.
- Aplicația nu funcționează pe emulator pentru partea de Bluetooth și microfon.
- Codul sursă este disponibil integral în repository-ul indicat.

---

**Autor:**  
Oros Claudia Alessandra 
E-mail : claudia.oros@student.upt.ro
