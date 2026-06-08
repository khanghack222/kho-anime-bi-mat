# 🎬 KHANG ANIME HUB - Động Anime Bí Mật Của Khang

> Trang web xem anime với bảo vệ PIN, hỗ trợ video từ GitHub Releases

![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=javascript&logoColor=black)

## ✨ Tính năng

- 🔒 **Bảo vệ PIN**: Yêu cầu nhập PIN thành viên để xem anime
- 🎥 **Video Player**: Video player tuyệt vời với controls đầy đủ
- 📱 **Responsive Design**: Hoạt động tốt trên desktop, tablet, mobile
- 🎨 **Dark Theme**: Giao diện tối hiện đại với accent cyan
- 📺 **Danh sách tập**: Dễ dàng chuyển đổi giữa các tập
- ⚡ **Tự động retry**: Tự động thử lại nếu video tải lỗi
- ⌨️ **Phím tắt**: Dùng `←` `→` để chuyển tập
- 🚀 **Tối ưu hiệu năng**: Lazy loading, smooth animations

## 🚀 Hướng dẫn sử dụng

### 1. Clone Repository
```bash
git clone https://github.com/khanghack222/kho-anime-bi-mat.git
cd kho-anime-bi-mat
```

### 2. Upload Video

**Bước 1**: Vào trang Releases của repo
- Link: https://github.com/khanghack222/kho-anime-bi-mat/releases

**Bước 2**: Tạo release mới
- Click "Create a new release"
- Tag name: `phim`
- Release title: `Videos - Thanh Gươm Diệt Quỷ Mùa 4`

**Bước 3**: Upload video
- Upload 8 file MP4 với tên: `tgdq-tap1.mp4` → `tgdq-tap8.mp4`
- Kích cỡ mỗi file <= 2GB

**Bước 4**: Publish Release

### 3. Cấu hình PIN

Mở `index.html` và tìm dòng:
```javascript
const PIN_CODE = 'khang2026';
```

Thay `'khang2026'` bằng PIN của bạn (ví dụ: `'1234'`)

### 4. Chạy Website

**Cách 1: GitHub Pages** (Miễn phí)
- Vào Settings → Pages
- Source: main branch
- Truy cập: https://khanghack222.github.io/kho-anime-bi-mat/

**Cách 2: Mở tệp trực tiếp**
- Right-click `index.html` → "Open with Browser"

**Cách 3: Local Server** (Khuyên dùng)
```bash
# Python 3
python -m http.server 8000

# Node.js
npx http-server

# PHP
php -S localhost:8000
```

Truy cập: http://localhost:8000

## 📝 PIN Mặc định

```
PIN: khang2026
```

## 🎮 Điều khiển

| Phím | Chức năng |
|------|----------|
| `←` | Tập trước |
| `→` | Tập sau |
| `Space` | Play/Pause |
| `F` | Fullscreen |
| `M` | Mute |

## 📂 Cấu trúc thư mục

```
kho-anime-bi-mat/
├── index.html          # File chính (HTML + CSS + JS)
├── README.md           # Hướng dẫn này
├── VIDEOS.md           # Danh sách video
├── .gitignore          # Git ignore file
└── videos/             # Thư mục chứa video (optional)
```

## 🔧 Tùy chỉnh

### Thay đổi màu sắc

Mở `index.html` và tìm CSS variables:
```css
:root {
    --primary: #00ffcc;        /* Cyan */
    --primary-dark: #00cc99;   /* Cyan đậm */
    --bg-dark: #1a1c23;        /* Nền tối */
    --error: #ff4757;          /* Đỏ lỗi */
}
```

### Thay đổi anime thông tin

Tìm và chỉnh sửa:
```javascript
<div class="anime-title" id="current-ep-title">Thanh Gươm Diệt Quỷ (Mùa 4)</div>
<div class="anime-sub">🌸 Vietsub lồng tiếng | Không quảng cáo</div>
```

### Thêm/Xóa tập

Chỉnh sửa array `episodes`:
```javascript
const episodes = [
    { name: 'Tập 01', file: 'tgdq-tap1.mp4' },
    { name: 'Tập 02', file: 'tgdq-tap2.mp4' },
    // Thêm/xóa tập ở đây
];
```

## 🐛 Troubleshooting

### Video không phát

**Vấn đề**: "❌ Không thể tải video"

**Giải pháp**:
1. Kiểm tra internet connection
2. Đảm bảo video đã upload vào Release
3. Kiểm tra URL Release có đúng không
4. Thử lại bằng nút "🔄 Thử lại"
5. Xóa cache browser (Ctrl+Shift+Delete)
6. Thử browser khác

### PIN không hoạt động

1. Kiểm tra lại PIN trong code
2. Xóa cache browser
3. Reload trang (Ctrl+F5)

### Video tải chậm

1. Kiểm tra kích cỡ file video
2. Nén video bằng FFmpeg:
```bash
ffmpeg -i input.mp4 -c:v libx264 -crf 28 -c:a aac -b:a 128k output.mp4
```
3. Upload lên alternative hosting (nếu file > 2GB)

## 📦 Dependencies

Không có dependencies! HTML + CSS + vanilla JavaScript

## 🌐 Browser Support

| Browser | Support |
|---------|----------|
| Chrome | ✅ Full |
| Firefox | ✅ Full |
| Safari | ✅ Full |
| Edge | ✅ Full |
| IE | ❌ No |

## 📜 License

Private - Chỉ dùng cho cá nhân

## 👤 Author

**Khang** - [@khanghack222](https://github.com/khanghack222)

## 💬 Hỗ trợ

Có vấn đề? [Mở Issue](https://github.com/khanghack222/kho-anime-bi-mat/issues)

---

**Last Updated**: 2026-06-08
**Version**: 1.0.0
