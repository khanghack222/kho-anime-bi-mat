import path from "path";
import fs from "fs";
import { fileURLToPath } from "url";
import { defineConfig, type Plugin } from "vite";
import { viteSingleFile } from "vite-plugin-singlefile";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const saveDataPlugin: Plugin = {
  name: "save-data-json",
  configureServer(server) {
    server.middlewares.use(async (req, res, next) => {
      if (req.method === "POST" && req.url === "/api/save-data") {
        let body = "";
        req.on("data", (chunk) => {
          body += chunk;
        });
        req.on("end", () => {
          try {
            const data = JSON.parse(body);
            const dataPath = path.resolve(__dirname, "data.json");
            fs.writeFileSync(dataPath, JSON.stringify(data, null, 2), "utf-8");
            res.writeHead(200, { "Content-Type": "application/json" });
            res.end(JSON.stringify({ success: true }));
          } catch (error) {
            res.writeHead(500, { "Content-Type": "application/json" });
            res.end(JSON.stringify({ success: false, error: (error as Error).message }));
          }
        });
        return;
      }
      next();
    });
  },
};

const copyStaticAssetsPlugin: Plugin = {
  name: "copy-static-assets",
  closeBundle() {
    const distPath = path.resolve(__dirname, "dist");
    if (fs.existsSync(distPath)) {
      for (const file of ["data.json", "catalog.json"]) {
        const src = path.resolve(__dirname, file);
        const dest = path.resolve(distPath, file);
        if (fs.existsSync(src)) {
          fs.copyFileSync(src, dest);
        }
      }
    }
  },
};

// https://vite.dev/config/
export default defineConfig({
  plugins: [viteSingleFile(), saveDataPlugin, copyStaticAssetsPlugin],
  server: {
    host: "0.0.0.0",
    port: 3000,
    allowedHosts: true,
  },
  preview: {
    host: "0.0.0.0",
    port: 3000,
  },
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "src"),
    },
  },
});
