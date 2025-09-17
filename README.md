## Setup Database

### 1. Buat Database di phpMyAdmin
- Buat database baru
- Import struktur tabel dari `tbd1.sql`

### 2. Generate Data 
Pilih salah satu dari 4 skenario berbeda:

#### Skenario 1 - Small Dataset (tableGen)
```bash
javac tbd1/tableGen.java && java -cp tbd1 tableGen
```
- Output: `tbd1/sql/all.sql`

#### Skenario 2 - Medium Dataset (tableGen2)
```bash
javac tbd1/tableGen2.java && java -cp tbd1 tableGen2
```
- Output: `tbd1/sql/all2.sql`

#### Skenario 3 - Large Dataset (tableGen3)
```bash
javac tbd1/tableGen3.java && java -cp tbd1 tableGen3
```
- Output: `tbd1/sql/all3.sql`

#### Skenario 4 - Extra Large Dataset (tableGen4)
```bash
javac tbd1/tableGen4.java && java -cp tbd1 tableGen4
```
- Output: `tbd1/sql/all4.sql`

### 3. Import ke phpMyAdmin
1. Pilih database yang sudah dibuat
2. Klik tab "Import"
3. Upload file SQL yang dihasilkan (all.sql, all2.sql, dll)
4. Klik "Go" untuk import
