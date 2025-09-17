package io.github.qe7.toolbox;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public final class GitUtil {

    private static final Properties props = new Properties();

    static {
        try (InputStream in = GitUtil.class.getClassLoader().getResourceAsStream("git.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getBranch() {
        return props.getProperty("git.branch", "unknown");
    }

    public static String getCommitId() {
        return props.getProperty("git.commit.id.abbrev", "unknown");
    }

    public static String getCommitTime() {
        return props.getProperty("git.commit.time", "unknown");
    }
}
