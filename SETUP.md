# 🚀 Hướng Dẫn Setup Hoàn Chỉnh

## Bước 1: Chuẩn Bị

### Yêu cầu
- GitHub account
- Browser hiện đại
- Video MP4 (8 tập)
- FFmpeg (optional - để nén video)

### Kiểm tra URL repo
```
https://github.com/khanghack222/kho-anime-bi-mat
```

---

## Bước 2: Clone hoặc Download

### Option A: Clone (khuyên dùng)
```bash
git clone https://github.com/khanghack222/kho-anime-bi-mat.git
cd kho-anime-bi-mat
```

### Option B: Download ZIP
1. Click Code → Download ZIP
2. Extract folder

---

## Bước 3: Cấu Hình

### Thay đổi PIN

Mở `index.html` với text editor, tìm:
```javascript
const PIN_CODE = 'khang2026';
```

Thay thành PIN của bạn, ví dụ:
```javascript
const PIN_CODE = '123456';
```

### Thay đổi tên anime

Tìm:
```html
<div class="anime-title" id="current-ep-title">Thanh Gươm Diệt Quỷ (Mùa 4)</div>
<div class="anime-sub">🌸 Vietsub lồng tiếng | Không quảng cáo</div>
```

Thay tên anime và mô tả.

---

## Bước 4: Upload Video

### 4A: Chuẩn Bị Video

**Kiểm tra định dạng**:
```bash
ffmpeg -i tgdq-tap1.mp4 -f null -
```

**Nén video (nếu file > 500MB)**:
```bash
# Quality cao (1080p)
ffmpeg -i tgdq-tap1.mp4 -c:v libx264 -crf 23 -c:a aac -b:a 192k tgdq-tap1-compressed.mp4

# Quality trung bình (720p)
ffmpeg -i tgdq-tap1.mp4 -vf scale=1280:720 -c:v libx264 -crf 25 -c:a aac -b:a 128k tgdq-tap1-compressed.mp4

# Quality thấp (480p - mạng chậm)
ffmpeg -i tgdq-tap1.mp4 -vf scale=854:480 -c:v libx264 -crf 28 -c:a aac -b:a 96k tgdq-tap1-compressed.mp4
```

### 4B: Upload lên GitHub Releases

1. Vào: https://github.com/khanghack222/kho-anime-bi-mat/releases

2. Click "Create a new release"

3. Điền thông tin:
   - **Tag name**: `phim` (QUAN TRỌNG - phải đúng chính xác)
   - **Release title**: `Videos - Thanh Gươm Diệt Quỷ Mùa 4`
   - **Description**:
   ```
   Tập 1-8 Thanh Gươm Diệt Quỷ Mùa 4
   Vietsub lồng tiếng
   Không quảng cáo
   ```

4. Upload video:
   - Click "Attach binaries by dropping them here or selecting them"
   - Chọn file: `tgdq-tap1.mp4` → `tgdq-tap8.mp4`
   - **Chờ upload xong** (có thể mất vài phút)

5. Click "Publish release"

---

## Bước 5: Test Locally

### Option A: Mở trực tiếp
```bash
# Windows
start index.html

# Mac
open index.html

# Linux
xdg-open index.html
```

### Option B: Local server (Khuyên dùng)

**Python 3**:
```bash
python -m http.server 8000
# Vào: http://localhost:8000
```

**Node.js**:
```bash
npx http-server
# Vào: http://localhost:8080
```

**PHP**:
```bash
php -S localhost:8000
# Vào: http://localhost:8000
```

### Test
1. Nhập PIN: `khang2026` (hoặc PIN bạn đặt)
2. Click unlock
3. Video phải load được
4. Thử click các tập khác

---

## Bước 6: Deploy lên GitHub Pages

### 6A: Cấu hình Repo

1. Vào Settings repo
2. Scroll xuống "Pages"
3. Source: 
   - Branch: `main`
   - Folder: `/ (root)`
4. Click Save

### 6B: Chờ Deploy

GitHub tự động deploy, chờ 1-2 phút

### 6C: Truy cập

Website sẽ có ở:
```
https://khanghack222.github.io/kho-anime-bi-mat/
```

---

## Bước 7: Chia sẻ

Có thể chia sẻ link:
```
https://khanghack222.github.io/kho-anime-bi-mat/
```

Hoặc download repo và chạy locally.

---

## 🔍 Kiểm Tra Lỗi

### Video không phát

**Kiểm tra**:
1. F12 → Console → có error gì?
2. Kiểm tra URL Release:
   ```
   https://github.com/khanghack222/kho-anime-bi-mat/releases/download/phim/tgdq-tap1.mp4
   ```
3. Tag release có đúng là `phim` không?
4. Video file name có đúng không?

**Sửa**:
- Xóa release cũ
- Tạo release mới với tag `phim`
- Upload lại video

### PIN không hoạt động

1. Kiểm tra lại code:
   ```javascript
   const PIN_CODE = 'khang2026'; // Đây là PIN
   ```
2. Xóa cache browser
3. Hard refresh: `Ctrl+Shift+Delete` (Windows) hoặc `Cmd+Shift+Delete` (Mac)
4. Reload page: `Ctrl+F5` hoặc `Cmd+Shift+R`

### Trang trắng/không load

1. Kiểm tra console (F12)
2. Kiểm tra file index.html có đúng không
3. Kiểm tra GitHub Pages Settings

---

## 📞 Cần Giúp?

- [GitHub Issues](https://github.com/khanghack222/kho-anime-bi-mat/issues)
- Check README.md
- Check VIDEOS.md

---

**Version**: 1.0.0  
**Updated**: 2026-06-08
