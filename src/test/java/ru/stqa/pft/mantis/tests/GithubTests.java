package ru.stqa.pft.mantis.tests;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

    @Test
    public void testCommits() throws IOException {
        Github github = new RtGithub("0d650dd6d1361db84b4a005aa263d87491b215da");
        RepoCommits commits = github.repos().get(new Coordinates.Simple("dk1991","JavaForTesterMaven")).commits();
        for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
            System.out.println(new RepoCommit.Smart(commit).message());
        }
    }
}
