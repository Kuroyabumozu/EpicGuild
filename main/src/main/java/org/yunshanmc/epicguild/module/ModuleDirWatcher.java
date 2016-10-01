package org.yunshanmc.epicguild.module;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * 模块目录监视器
 * <p>
 * 作者： Yun-Shan <br>
 * 创建日期： 2016/9/27.
 */
class ModuleDirWatcher extends Thread {
    
    private final WatchService watcher;
    private final Path         dir;
    
    private final EGModuleManager moduleManager;
    
    private volatile boolean close;
    
    public ModuleDirWatcher(Path dir, EGModuleManager moduleManager) throws IOException {
        this.dir = dir;
        this.moduleManager = moduleManager;
        
        this.watcher = FileSystems.getDefault().newWatchService();
        // 注册给定的目录到监视服务
        dir.register(watcher, ENTRY_CREATE, ENTRY_MODIFY);
    }
    
    @Override
    public void run() {
        while (true) {
            if (this.close) break;
            // 等待监视事件发生
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                // 忽略异常
                //TODO 异常记录
                continue;
            }
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();
                if (kind != ENTRY_CREATE && kind != ENTRY_MODIFY) {// 仅限创建和修改
                    continue;
                }
                WatchEvent<Path> evt = (WatchEvent<Path>) event;
                Path name = evt.context();
                Path child = this.dir.resolve(name);
                if (Files.isRegularFile(child, NOFOLLOW_LINKS)) {
                    this.moduleManager.addModuleFile(child.toFile());
                }
            }
        }
        try {
            this.watcher.close();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO 异常记录
        }
    }
    
    public void close() {
        this.close = true;
    }
}
