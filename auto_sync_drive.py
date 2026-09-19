import os
import sys
import re
import json
import subprocess
import xml.etree.ElementTree as ET
import urllib.request
import urllib.parse

NYAA_RSS_URLS = [
    "https://nyaa.si/?page=rss&q=1080p+SubsPlease&c=1_2&f=0",
    "https://nyaa.si/?page=rss&q=1080p+Erai-raws&c=1_2&f=0"
]

MAX_DOWNLOADS = int(os.environ.get("MAX_EPISODES", 20))
DEST_FOLDER = os.environ.get("DRIVE_DEST", "gdrive:Anime_Clean_5TB")

def parse_rss():
    items = []
    headers = {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"}
    for url in NYAA_RSS_URLS:
        try:
            req = urllib.request.Request(url, headers=headers)
            with urllib.request.urlopen(req, timeout=15) as resp:
                xml_data = resp.read()
                root = ET.fromstring(xml_data)
                for item in root.findall(".//item"):
                    title = item.find("title").text if item.find("title") is not None else ""
                    link = item.find("link").text if item.find("link") is not None else ""
                    category = item.find("category").text if item.find("category") is not None else ""
                    
                    # Look for magnet or torrent url
                    items.append({
                        "title": title,
                        "link": link
                    })
        except Exception as e:
            print(f"[!] Lỗi đọc RSS {url}: {e}")
    return items

def sanitize_name(name):
    clean = re.sub(r"\[.*?\]|\(.*?\)", "", name)
    clean = re.sub(r"[\/\\:\*\?\"<>\|]", "", clean)
    return clean.strip()

def main():
    print(f"[*] Bắt đầu quét anime sạch... Max: {MAX_DOWNLOADS}")
    items = parse_rss()
    print(f"[*] Tìm thấy {len(items)} tập torrent sạch.")

    os.makedirs("downloads", exist_ok=True)
    downloaded_count = 0
    catalog = []

    if os.path.exists("catalog.json"):
        try:
            with open("catalog.json", "r", encoding="utf-8") as f:
                catalog = json.load(f)
        except:
            catalog = []

    existing_titles = {x.get("title", "") for x in catalog}

    for it in items:
        if downloaded_count >= MAX_DOWNLOADS:
            break
        
        raw_title = it["title"]
        clean_title = sanitize_name(raw_title)
        
        # Check if already processed
        if any(clean_title in ex for ex in existing_titles):
            continue

        torrent_url = it["link"]
        print(f"\n[+] Đang kéo tập: {clean_title}")

        # Use aria2c to download torrent fast
        cmd_aria = [
            "aria2c",
            "--seed-time=0",
            "--max-connection-per-server=8",
            "--split=8",
            "--summary-interval=5",
            "-d", "downloads",
            torrent_url
        ]

        ret = subprocess.run(cmd_aria)
        if ret.returncode == 0:
            downloaded_count += 1
            print(f"[✓] Đã tải xong: {clean_title}")
            
            # Check if rclone is configured to upload to gdrive
            if os.environ.get("RCLONE_CONFIG_DATA"):
                print(f"[>] Đang đẩy file sang Google Drive 5TB: {DEST_FOLDER}")
                subprocess.run(["rclone", "copy", "downloads", DEST_FOLDER, "--progress", "--drive-chunk-size", "64M"])
                # Xóa local sau khi upload
                for f in os.listdir("downloads"):
                    file_p = os.path.join("downloads", f)
                    if os.path.isfile(file_p):
                        os.remove(file_p)

    print(f"\n[*] Hoàn tất lượt tải. Đã xử lý {downloaded_count} tập.")

if __name__ == "__main__":
    main()
