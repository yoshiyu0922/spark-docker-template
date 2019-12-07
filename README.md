# spark-docker-template

## overview

- sparkのローカル実行環境のテンプレート
- dockerのコンテナにhadoop, spark, hive環境を構築（詳細は[Dockerfile](./docker/Dockerfile)）

## setup

以下の通りに実行して、イメージとコンテナを作成

```bash
$ cd out
$ docker-compose up --build
```

## jarを作成・実行

以下の通りに実行して、jarを作成

```bash
$ sbt assembly
```

作成したjarは`out`フィルダに作成される

作成したjarをコンテナにコピー

```bash
$ docker cp ./out/spark-sample-0.1-SNAPSHOT.jar (コンテナID):/sample/
```

コンテナにログインして、jarを実行する

```bash
$ docker exec -it (コンテナID) bash
# cd sample/
# spark-submit --class example.HelloWorld spark-sample-0.1-SNAPSHOT.jar 
```

## コンテナに置かれるshell

- [beeline.sh](./docker/files/beeline.sh)
  - beelineでhiveにログイン
  - beeline.shを使わずにbeelineでログインできる
  ```
  # beeline --showHeader=true -u jdbc:hive2://localhost:10000
  ```
  - beeline.shを使えば、`rlwrap`により履歴や補完ができるようになる
- [start.sh](./docker/files/start.sh)
  - コンテナを起動する際に実行されるshell
- [initializeHive.sh](./docker/files/initializeHive.sh)
  - データベースの初期化（データベースとテーブルの作成）
  - [start.sh](./docker/files/start.sh)の中で、[initializeHive.sh](./docker/files/initializeHive.sh)が実行される