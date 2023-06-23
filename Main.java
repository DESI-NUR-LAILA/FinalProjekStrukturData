import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

// Kelas AyamBakar sebagai objek ayam bakar
class AyamBakar {
    private String namaPelanggan;
    private int harga;
    private int jumlah;
    private String namaAyam;
    private int nomorAntrian;

    public AyamBakar(String namaPelanggan, String namaAyam, int harga, int jumlah) {
        this.namaPelanggan = namaPelanggan;
        this.namaAyam = namaAyam;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public int getHarga() {
        return harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getNamaAyam() {
        return namaAyam;
    }

    public int getNomorAntrian() {
        return nomorAntrian;
    }

    public void setNomorAntrian(int nomorAntrian) {
        this.nomorAntrian = nomorAntrian;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}

// Kelas PenjualanAyamBakar untuk mengelola penjualan ayam bakar
class PenjualanAyamBakar {
    private ArrayList<AyamBakar> daftarAyam;
    private Queue<AyamBakar> antrianPesanan;
    private Stack<AyamBakar> stokAyam;

    public PenjualanAyamBakar() {
        daftarAyam = new ArrayList<>();
        antrianPesanan = new LinkedList<>();
        stokAyam = new Stack<>();
    }

    public void tambahAyam(String nama, int harga) {
        AyamBakar ayam = new AyamBakar(nama, nama, harga, 0);
        daftarAyam.add(ayam);
        stokAyam.push(ayam);
    }

    public void tampilkanMenu() {
        System.out.println("=== Daftar Menu ===");
        for (AyamBakar ayam : daftarAyam) {
            System.out.println(ayam.getNamaAyam() + " - Rp" + ayam.getHarga());
        }
    }

    public void pesanAyam(String namaPelanggan, String namaAyam, int jumlah) {
        AyamBakar ayam = null;
        for (AyamBakar a : daftarAyam) {
            if (a.getNamaAyam().equalsIgnoreCase(namaAyam)) {
                ayam = a;
                break;
            }
        }

        if (ayam != null) {
            int nomorAntrian = antrianPesanan.size() + 1;
            AyamBakar pesanan = new AyamBakar(namaPelanggan, namaAyam, ayam.getHarga(), jumlah);
            antrianPesanan.offer(pesanan);
            System.out.println("Pesanan dengan nomor antrian " + nomorAntrian + " telah ditambahkan untuk " + namaPelanggan);
        } else {
            System.out.println("Ayam bakar dengan nama " + namaAyam + " tidak ditemukan.");
        }
    }

    public void tampilkanAntrianPesanan() {
        if (antrianPesanan.isEmpty()) {
            System.out.println("Antrian pesanan kosong.");
        } else {
            System.out.println("=== Antrian Pesanan ===");
            int nomorAntrian = 1;
            for (AyamBakar pesanan : antrianPesanan) {
                System.out.println("Nomor Antrian: " + nomorAntrian);
                System.out.println("Nama Pelanggan: " + pesanan.getNamaPelanggan());
                System.out.println("Menu Pesanan: " + pesanan.getNamaAyam());
                System.out.println("Jumlah Pesanan: " + pesanan.getJumlah());
                System.out.println("--------------------------");
                nomorAntrian++;
            }
        }
    }

    public void ambilPesanan() {
        AyamBakar pesanan = antrianPesanan.poll();

        if (pesanan != null) {
            int nomorAntrian = pesanan.getNomorAntrian();
            System.out.println("Pesanan dengan nomor antrian " + (nomorAntrian + 1) + " diambil.");
            System.out.println("Detail Pesanan:");
            System.out.println("Nama Pelanggan: " + pesanan.getNamaPelanggan());
            System.out.println("Menu Pesanan: " + pesanan.getNamaAyam());
            System.out.println("Jumlah Pesanan: " + pesanan.getJumlah());
            System.out.println("Total Harga: " + pesanan.getHarga() * pesanan.getJumlah());

            // Perbarui nomor antrian pada pesanan-pesanan yang tersisa
            for (AyamBakar antrian : antrianPesanan) {
                antrian.setNomorAntrian(antrian.getNomorAntrian() + 1);
            }

            // Kurangi stok ayam yang dipesan dari daftar stokAyam
            for (AyamBakar ayam : stokAyam) {
                if (ayam.getNamaAyam().equalsIgnoreCase(pesanan.getNamaAyam())) {
                    int sisaStok = ayam.getJumlah() - pesanan.getJumlah();
                    ayam.setJumlah(sisaStok);
                    break;
                }
            }
        } else {
            System.out.println("Tidak ada pesanan dalam antrian.");
        }
    }

    public void tampilkanStokAyam() {
        System.out.println("=== Stok Ayam ===");
        for (AyamBakar ayam : stokAyam) {
            int jumlahPesanan = 0;
            for (AyamBakar pesanan : antrianPesanan) {
                if (pesanan.getNamaAyam().equalsIgnoreCase(ayam.getNamaAyam())) {
                    jumlahPesanan += pesanan.getJumlah();
                }
            }
            int sisaStok = 100 - jumlahPesanan;
            System.out.println(ayam.getNamaAyam() + " - Stok: " + sisaStok);
        }
    }

    public void urutkanMenuBerdasarkanHarga() {
        Collections.sort(daftarAyam, (a1, a2) -> a1.getHarga() - a2.getHarga());
        System.out.println("=== Daftar Menu Ayam Bakar Urut Berdasarkan Harga ===");
        for (AyamBakar ayam : daftarAyam) {
            System.out.println(ayam.getNamaAyam() + " - Rp" + ayam.getHarga());
        }
    }
}

// Kelas Utama sebagai tempat menjalankan program
public class Main {
    public static void main(String[] args) {
        PenjualanAyamBakar penjualan = new PenjualanAyamBakar();

        penjualan.tambahAyam("Ayam Bakar", 10000);
        penjualan.tambahAyam("Ati Ampela Bakar", 15000);
        penjualan.tambahAyam("Usus Bakar", 12000);
        penjualan.tambahAyam("Sate", 11000);

        Scanner scanner = new Scanner(System.in);
        int pilihan = 0;

        while (pilihan != 7) {
            System.out.println("=== Sistem Penjualan Bakaran Milenial ===");
            System.out.println("1. Tampilkan Menu Bakaran Milenial");
            System.out.println("2. Pesan Bakaran Milenial");
            System.out.println("3. Tampilkan Antrian Pesanan");
            System.out.println("4. Ambil Pesanan");
            System.out.println("5. Tampilkan Stok Ayam");
            System.out.println("6. Urutkan Menu Berdasarkan Harga");
            System.out.println("7. Keluar");
            System.out.print("Pilihan Anda: ");
            pilihan = scanner.nextInt();

            switch (pilihan) {
                case 1:
                    penjualan.tampilkanMenu();
                    break;

                case 2:
                    scanner.nextLine(); // Membersihkan newline yang masih tersisa

                    System.out.print("Masukkan nama pelanggan: ");
                    String namaPelanggan = scanner.nextLine();

                    System.out.print("Masukkan nama menu yang ingin dipesan: ");
                    String namaAyam = scanner.nextLine();

                    System.out.print("Masukkan jumlah menu pesanan: ");
                    int jumlahPesanan = scanner.nextInt();

                    penjualan.pesanAyam(namaPelanggan, namaAyam, jumlahPesanan);
                    break;

                case 3:
                    penjualan.tampilkanAntrianPesanan();
                    break;

                case 4:
                    penjualan.ambilPesanan();
                    break;

                case 5:
                    penjualan.tampilkanStokAyam();
                    break;

                case 6:
                    penjualan.urutkanMenuBerdasarkanHarga();
                    break;

                case 7:
                    System.out.println("Terima kasih telah menggunakan Sistem Penjualan Ayam Bakar Milenial.");
                    break;

                default:
                    System.out.println("Pilihan tidak valid.");
                    break;
            }

            System.out.println();
        }
    }
}
