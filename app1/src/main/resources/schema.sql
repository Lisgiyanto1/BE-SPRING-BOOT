-- Table: proyek
CREATE TABLE IF NOT EXISTS proyek (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nama_proyek VARCHAR(255) NOT NULL,
  client VARCHAR(255),
  tgl_mulai DATETIME,
  tgl_selesai DATETIME,
  pimpinan_proyek VARCHAR(255),
  keterangan TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: lokasi
CREATE TABLE IF NOT EXISTS lokasi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama_lokasi VARCHAR(255) NOT NULL,
    negara VARCHAR(255) NOT NULL,
    provinsi VARCHAR(255) NOT NULL,
    kota VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: proyek_lokasi
CREATE TABLE IF NOT EXISTS proyek_lokasi (
  proyek_id INT,
  lokasi_id INT,
  FOREIGN KEY (proyek_id) REFERENCES proyek(id),
  FOREIGN KEY (lokasi_id) REFERENCES lokasi(id)
);