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

// https://vite.dev/config/
export default defineConfig({
  plugins: [viteSingleFile(), saveDataPlugin],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "src"),
    },
  },
});
