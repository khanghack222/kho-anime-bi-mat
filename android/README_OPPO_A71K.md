# Hướng dẫn Build APK cho Oppo A71k (Android 7.1.1 Nougat - ColorOS 3.2)

Dự án này chứa mã nguồn Native Android wrapper hoàn chỉnh cho **KhangHub Anime** nằm trong thư mục `/android`.

## 📱 Cấu hình tối ưu riêng cho Oppo A71k:
* **Chipset:** Qualcomm Snapdragon 450 (8 nhân Cortex-A53 1.8 GHz)
* **GPU:** Adreno 506 (Bật tăng tốc phần cứng `android:hardwareAccelerated="true"`)
* **RAM:** 2GB / 3GB (Khai báo `android:largeHeap="true"` để chống tràn bộ nhớ)
* **Hệ điều hành:** Android 7.1.1 Nougat (API Level 25)
* **minSdkVersion:** `21` (Hỗ trợ từ Android 5.0 trở lên, tương thích 100% với Android 7.1.1)
* **targetSdkVersion:** `34` (Chuẩn Android mới nhất)
* **Mạng:** Kích hoạt `android:usesCleartextTraffic="true"` để chạy mượt mà các luồng stream HLS `.m3u8` và proxy.
* **Màn hình:** Tự động xoay ngang toàn màn hình 16:9 HD khi xem video, giữ màn hình luôn sáng (`FLAG_KEEP_SCREEN_ON`).

---

## 🚀 Cách 1: Build tự động bằng GitHub Actions (Khuyên dùng)
Dự án đã có sẵn workflow CI/CD tại `/.github/workflows/build-apk.yml`.
1. Đẩy mã nguồn lên GitHub repository của bạn.
2. Vào tab **Actions** trên GitHub.
3. Chọn workflow **"Build Android APK"** và nhấn **Run workflow**.
4. Sau 2 phút, GitHub sẽ xuất bản file `app-release.apk` tại mục **Releases** hoặc **Artifacts** để bạn tải về máy Oppo A71k.

---

## 💻 Cách 2: Build bằng Android Studio trên máy tính
1. Mở Android Studio, chọn **Open** và trỏ đến thư mục `/android`.
2. Đợi Gradle đồng bộ (Gradle wrapper đã dùng bản 8.2 và AGP 8.2.2 tương thích Java 17).
3. Vào menu **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)**.
4. File `.apk` sẽ được tạo tại:
   `android/app/build/outputs/apk/debug/app-debug.apk` hoặc `release/app-release.apk`.
5. Chép file `.apk` vào điện thoại Oppo A71k qua cáp USB hoặc thẻ nhớ.

---

## ⚡ Cách 3: Cài đặt trực tiếp 1 chạm không cần build máy tính (WebAPK)
1. Mở ứng dụng KhangHub bằng trình duyệt **Google Chrome** trên điện thoại Oppo A71k.
2. Nhấn vào nút **"📲 Cài App Oppo A71k"** ở thanh công cụ hoặc nhấn dấu 3 chấm `⋮` của Chrome → chọn **"Thêm vào màn hình chính" (Add to Home screen)**.
3. Chrome và Google Play Services sẽ tự động biên dịch và tạo một file ứng dụng WebAPK cài trực tiếp lên màn hình chính Oppo A71k.
