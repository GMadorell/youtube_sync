
# Youtube Sync
Automatically synchronize YouTube playlists to the local filesystem. Whenever you add a new video to a playlist, it 
will automatically get downloaded.

## Dependencies
- Youtube-dl  (brew install youtube-dl)
- FFmpeg  (brew install ffmpeg)

# Development
## Use the git hooks in doc/hook
* rm -rf .git/hooks
* ln -s ../doc/hook .git/hooks
* setup permissions as needed on the hooks files
