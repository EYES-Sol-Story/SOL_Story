import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:sol_story/Sungjun/models/story_data.dart'; // StoryDataModel 가져오기

class StoryOwn extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final storyDataModel = Provider.of<StoryDataModel>(context);

    return Scaffold(
      appBar: AppBar(
        title: Text('저장된 스토리'),
      ),
      body: storyDataModel.stories.isEmpty
          ? Center(
        child: Text('저장된 스토리가 없습니다.'),
      )
          : ListView.builder(
        itemCount: storyDataModel.stories.length,
        itemBuilder: (context, index) {
          final story = storyDataModel.stories[index];
          return ListTile(
            title: Text(story.title),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => StoryDetailPage(story: story),
                ),
              );
            },
            trailing: IconButton(
              icon: Icon(Icons.delete),
              onPressed: () {
                _showDeleteConfirmationDialog(context, storyDataModel, index);
              },
            ),
          );
        },
      ),
    );
  }

  void _showDeleteConfirmationDialog(BuildContext context, StoryDataModel storyDataModel, int index) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('스토리 삭제'),
          content: Text('이 스토리를 삭제하시겠습니까?'),
          actions: <Widget>[
            TextButton(
              child: Text('취소'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            TextButton(
              child: Text('삭제'),
              onPressed: () {
                storyDataModel.removeStory(index);
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }
}

class StoryDetailPage extends StatelessWidget {
  final Story story;

  StoryDetailPage({required this.story});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(story.title),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: SingleChildScrollView(
          child: Text(story.content),
        ),
      ),
    );
  }
}
