package com.example.Member.Client;

import com.example.Material.MaterialResponses.TopicResponse;
import com.example.Member.MemberResponse.ResponseModel;
import org.springframework.http.ResponseEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.concurrent.CompletableFuture;

public interface StudyMaterialClient
{
    @GET("/StudyMaterials/getTopic/{topicId}")
    Call<ResponseModel<TopicResponse>> getTopic(@Path("topicId") String topicId);

}
