package com.promote.service.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GitHookData implements Serializable {

    public int user_id;
    public int project_id;
    public String ref;//"refs/heads/release_0.0.0.1",
    public String event_name;//提交类型 repository_update push tag_push
    public String user_name;
    public String user_username;
    public List<Commits> commits;
    public Project project;
    public Repository repository;
    public int  total_commits_count;

    public class Project {
        public String name;
        public String git_ssh_url;
        public String git_http_url;
        public String namespace;
        public String path_with_namespace;
        public String default_branch;
    }

    public class Repository {
        public String name;
        public String git_ssh_url;
        public String git_http_url;
    }

    public class Commits {
        public String id;
        public String message;
        public String url;
        public String timestamp;
        public Author author;

        public class Author {
            public String name;
            public String email;
        }
    }


}
