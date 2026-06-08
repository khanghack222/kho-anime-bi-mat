# 📺 Danh Sách Video - Thanh Gươm Diệt Quỷ (Mùa 4)

## 🎬 Hướng dẫn Upload Video

### Bước 1: Tạo Release

1. Vào: https://github.com/khanghack222/kho-anime-bi-mat/releases
2. Click "Create a new release"
3. Điền thông tin:
   - **Tag name**: `phim`
   - **Release title**: `Videos - Thanh Gươm Diệt Quỷ Mùa 4`
   - **Description**: Các video anime (8 tập)

### Bước 2: Upload Video

Drag and drop hoặc click "Attach binaries" để upload:

```
Tập 01 → tgdq-tap1.mp4
Tập 02 → tgdq-tap2.mp4
Tập 03 → tgdq-tap3.mp4
Tập 04 → tgdq-tap4.mp4
Tập 05 → tgdq-tap5.mp4
Tập 06 → tgdq-tap6.mp4
Tập 07 → tgdq-tap7.mp4
Tập 08 → tgdq-tap8.mp4
```

### Bước 3: Publish

Click "Publish release"

---

## 📥 Video Links

Sau khi upload, các video sẽ có thể tải từ:

- [tgdq-tap1.mp4](https://github.com/khanghack222/kho-anime-bi-mat/releases/download/phim/tgdq-tap1.mp4)
- [tgdq-tap2.mp4](https://github.com/khanghack222/kho-anime-bi-mat/releases/download/phim/tgdq-tap2.mp4)
- [tgdq-tap3.mp4](https://github.com/khanghack222/kho-anime-bi-mat/releases/download/phim/tgdq-tap3.mp4)
- [tgdq-tap4.mp4](https://github.com/khanghack222/kho-anime-bi-mat/releases/download/phim/tgdq-tap4.mp4)
- [tgdq-tap5.mp4](https://github.com/khanghack222/kho-anime-bi-mat/releases/download/phim/tgdq-tap5.mp4)
- [tgdq-tap6.mp4](https://github.com/khanghack222/kho-anime-bi-mat/releases/download/phim/tgdq-tap6.mp4)
- [tgdq-tap7.mp4](https://github.com/khanghack222/kho-anime-bi-mat/releases/download/phim/tgdq-tap7.mp4)
- [tgdq-tap8.mp4](https://github.com/khanghack222/kho-anime-bi-mat/releases/download/phim/tgdq-tap8.mp4)

---

## ⚠️ Giới hạn

- **Kích cỡ tối đa**: 2GB/file (GitHub Releases limit)
- **Định dạng**: MP4, WebM, Ogg
- **Bitrate khuyến nghị**: 1-5 Mbps

---

## 💡 Tips

### Nén video để giảm kích cỡ

```bash
# Cài FFmpeg trước
ffmpeg -i input.mp4 -c:v libx264 -crf 28 -c:a aac -b:a 128k output.mp4
```

### Kiểm tra video

```bash
ffmpeg -i file.mp4 -f null -
```

### Convert sang MP4

```bash
ffmpeg -i input.avi -c:v libx264 -c:a aac output.mp4
```

---

**Updated**: 2026-06-08
